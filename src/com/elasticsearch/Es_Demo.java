package com.elasticsearch;

import com.thread.my_thread.Task;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.count.CountRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.List;

/**
 * Created by lw on 14-7-7.
 * <p/>
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 */
public class Es_Demo {

    private Client client;

    private static final String INDEX_DEMO_01 = "index_demo_01";
    private static final String INDEX_DEMO_01_MAPPING = "index_demo_01_mapping";

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * startup Transport Client
     *
     * @return
     */
    public Client buildClient() {
        /**
         * 可以设置client.transport.sniff为true来使客户端去嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中，
         * 这样做的好 处是一般你不用手动设置集群里所有集群的ip到连接客户端，它会自动帮你添加，并且自动发现新加入集群的机器。
         */
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true).put("cluster.name", "liw_test").build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        //.addTransportAddress(new InetSocketTransportAddress("10.211.55.4", 9300));
        client.admin().indices().exists(new IndicesExistsRequest(INDEX_DEMO_01)).actionGet().isExists();
        return client;
    }

    /**
     * on shutDownClient
     */
    public void shutDownClient() {
        client.close();
    }

    /**
     * 预定义一个索引的mapping,使用mapping的好处是可以个性的设置某个字段等的属性
     * INDEX_DEMO_01类似于数据库
     * mapping 类似于预设某个表的字段类型
     * <p/>
     * Mapping,就是对索引库中索引的字段名及其数据类型进行定义，类似于关系数据库中表建立时要定义字段名及其数据类型那样，
     * 不过es的 mapping比数据库灵活很多，它可以动态添加字段。
     * 一般不需要要指定mapping都可以，因为es会自动根据数据格式定义它的类型，
     * 如果你需要对某 些字段添加特殊属性（如：定义使用其它分词器、是否分词、是否存储等），就必须手动添加mapping。
     * 有两种添加mapping的方法，一种是定义在配 置文件中，一种是运行时手动提交mapping，两种选一种就行了。
     *
     * @throws Exception
     */
    public void buildIndexMapping() throws Exception {
        //在本例中主要得注意,ttl及timestamp如何用java ,这些字段的具体含义,请去到es官网查看
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate(INDEX_DEMO_01);
        XContentBuilder mapping = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("we3r")//
                .startObject("_ttl")//有了这个设置,就等于在这个给索引的记录增加了失效时间,
                        //ttl的使用地方如在分布式下,web系统用户登录状态的维护.
                .field("enabled", true)//默认的false的
                .field("default", "5m")//默认的失效时间,d/h/m/s 即天/小时/分钟/秒
                .field("store", "yes")
                .field("index", "not_analyzed")
                .endObject()
                .startObject("_timestamp")//这个字段为时间戳字段.即你添加一条索引记录后,自动给该记录增加个时间字段(记录的创建时间),搜索中可以直接搜索该字段.
                .field("enabled", true)
                .field("store", "no")
                .field("index", "not_analyzed")
                .endObject()
                        //properties下定义的name等等就是属于我们需要的自定义字段了,相当于数据库中的表字段 ,此处相当于创建数据库表
                .startObject("properties")
                .startObject("name").field("type", "string").field("store", "yes").endObject()
                .startObject("home").field("type", "string").field("index", "not_analyzed").endObject()
                .startObject("height").field("type", "double").endObject()
                .startObject("age").field("type", "integer").endObject()
                .startObject("birthday").field("type", "date").field("format", "YYYY-MM-dd").endObject()
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping(INDEX_DEMO_01_MAPPING, mapping);
        cib.execute().actionGet();
    }

    /**
     * 增加索引记录
     *
     * @param user 添加的记录
     * @throws Exception
     */
    public void buildIndex(User user) throws Exception {
        // productindex_liwei为上个方法中定义的索引,prindextype为类型.jk8231为id,以此可以代替memchche来进行数据的缓存
        IndexResponse response = client.prepareIndex(INDEX_DEMO_01, INDEX_DEMO_01_MAPPING)
                .setSource(
                        User.getXContentBuilder(user)
                )
                .setTTL(8000)//这样就等于单独设定了该条记录的失效时间,单位是毫秒,必须在mapping中打开_ttl的设置开关
                .execute()
                .actionGet();
    }

    /**
     * 批量添加记录到索引
     *
     * @param userList 批量添加数据
     * @throws IOException
     */
    public void buildBulkIndex(List<User> userList) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();
        // either use client#prepare, or use Requests# to directly build index/delete requests

        for (User user : userList) {
            //通过add批量添加
            bulkRequest.add(client.prepareIndex(INDEX_DEMO_01, INDEX_DEMO_01_MAPPING)
                            .setSource(
                                    User.getXContentBuilder(user)
                            )
            );
        }

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        //如果失败
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
            System.out.println("buildFailureMessage:" + bulkResponse.buildFailureMessage());
        }
    }

    /**
     * 通过Id删除索引记录
     *
     * @param id
     */
    public void deleteIndexById(String id) {

        DeleteResponse responsedd = client.prepareDelete(INDEX_DEMO_01, INDEX_DEMO_01_MAPPING, id)
                .setOperationThreaded(false)
                .execute()
                .actionGet();
    }

    /**
     * 通过Query条件查询后删除索引记录
     */
    public void deleteIndexByQuery() {

        QueryBuilder query = new QueryStringQueryBuilder("李伟").field("name");
        client.prepareDeleteByQuery(INDEX_DEMO_01).setQuery(query).execute().actionGet();

    }

    /**
     * 搜索，通过Id搜索API
     *
     * @param id
     */
    public void searchById(String id) {
        GetResponse responsere = client.prepareGet(INDEX_DEMO_01, INDEX_DEMO_01_MAPPING, id)
                /*
                设置线程
     当删除api在同一个节点上执行时（在一个分片中执行一个api会分配到同一个服务器上），
     删除api允许执行前设置线程模式 （operationThreaded选项），operationThreaded这个选项是使这个操作在另外一个线程中执行，
     或在一个正在请求的线程 （假设这个api仍是异步的）中执行。
     默认的话operationThreaded会设置成true，这意味着这个操作将在一个不同的线程中执行。
     下面是 设置成false的方法：
                 */
                .setOperationThreaded(false)
                .execute()
                .actionGet();
        if (responsere.isExists()) {
            System.out.println("通过Id=[" + id + "]搜索结果:\n" + responsere.getSourceAsString());
        } else {
            System.out.println("通过Id=[" + id + "]搜索结果:不存在");
        }

    }

    /**
     * 搜索，Query搜索API
     * 条件组合查询
     */
    public void searchByQuery() {

        //qb1构造了一个TermQuery，对name这个字段进行项搜索，项是最小的索引片段，这个查询对应lucene本身的TermQuery
        QueryBuilder queryBuilder1 = QueryBuilders.termQuery("name", "葫芦2娃");

        //qb2构造了一个组合查询（BoolQuery），其对应lucene本身的BooleanQuery，可以通过must、should、mustNot方法对QueryBuilder进行组合，形成多条件查询
        QueryBuilder queryBuilder2 = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("note", "test1"))
                .must(QueryBuilders.termQuery("note", "test4"))
                .mustNot(QueryBuilders.termQuery("note", "test2"))
                .should(QueryBuilders.termQuery("note", "test3"));

        //直接执行搜索
        SearchHit[] searchHitsBySearch = client.search(new SearchRequest(INDEX_DEMO_01)
                        .types(INDEX_DEMO_01_MAPPING)
                        .source(
                                SearchSourceBuilder.searchSource()
                                        .sort("age")
                        )
        )
                .actionGet()
                .getHits()
                .hits();


        //预准备执行搜索

        client.prepareSearch(INDEX_DEMO_01)
                .setTypes(INDEX_DEMO_01_MAPPING)
                        // .setSearchType(SearchType.SCAN)
                //.setQuery(queryBuilder1)
                        //.setQuery(QueryBuilders.termQuery("multi", "test"))       // Query
                //.setPostFilter(FilterBuilders.rangeFilter("age").lt(10).gt(50))   // Filter过滤
                //.setPostFilter(FilterBuilders.inFilter("age", 45))   // Filter过滤
                .setPostFilter(FilterBuilders.boolFilter().mustNot(FilterBuilders.inFilter("age", 20, 21, 22)))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                        //注册监听事件
                .addListener(new ActionListener<SearchResponse>() {
                    @Override
                    public void onResponse(SearchResponse searchResponse) {
                        SearchHit[] searchHitsByPrepareSearch = searchResponse.getHits().hits();
                        //获取结果集
                        for (SearchHit searchHit : searchHitsByPrepareSearch) {
                            System.out.println(searchHit.getSourceAsString());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }

    /**
     * 搜索，Query搜索API
     * count查询
     */
    public void searchByQuery_Count() {

        long countByCount = client.count(
                new CountRequest(INDEX_DEMO_01).types(INDEX_DEMO_01_MAPPING)
        )
                .actionGet()
                .getCount();

        //预准备
        long countByPrepareCount = client.prepareCount(INDEX_DEMO_01)
                .setTypes(INDEX_DEMO_01_MAPPING)
                .setQuery(QueryBuilders.termQuery("name", "葫芦1娃"))
                .execute()
                .actionGet()
                .getCount();
        System.out.println("searchByQuery_Count<{}>:" + countByCount);
    }

    /**
     * 修改
     */
    public void updateByQuery() throws IOException {

        boolean isCreatedByUpdate = client.update(new UpdateRequest(INDEX_DEMO_01, INDEX_DEMO_01_MAPPING, "TKLkVot6SJu429zpJbFN3g")
                        .doc(XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("name", "liw")
                                        .field("age", "25")
                                        .endObject()
                        )
        )
                .actionGet()
                .isCreated();
        //预准备
        client.prepareUpdate(INDEX_DEMO_01, INDEX_DEMO_01_MAPPING, "TKLkVot6SJu429zpJbFN3g")
                .setDoc("age", "18")
                .execute()
                .actionGet();
    }


    public static void main(String[] dfd) {
        Es_Demo esm = new Es_Demo();
        esm.setClient(esm.buildClient());

        try {
            //esm.buildIndexMapping();
            //esm.buildIndex(User.getOneRandomUser());
            //esm.buildBulkIndex(User.getRandomUsers(10000));
            //esm.searchById("5_XDJBA-TBOra4a7oyiYrA");
            esm.searchByQuery();
            //esm.searchByQuery_Count();
            //esm.updateByQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            esm.shutDownClient();
        }
    }
}
