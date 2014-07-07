package com.elasticsearch;

import com.util.date.Joda_Time;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
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

import java.io.IOException;
import java.util.Date;

/**
 * Created by lw on 14-7-7.
 * <p/>
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 */
public class Es_Demo {
    private Client client;

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
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true).put("cluster.name", "liw_test").build();
        Client client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
        // .addTransportAddress(new InetSocketTransportAddress("10.211.55.4", 9300));
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
     *
     * @throws Exception
     */
    public void buildIndexMapping() throws Exception {
        //在本例中主要得注意,ttl及timestamp如何用java ,这些字段的具体含义,请去到es官网查看
        CreateIndexRequestBuilder cib = client.admin().indices().prepareCreate("productindex_liwei");
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
                .startObject("hight").field("type", "double").endObject()
                .startObject("iswoman").field("type", "boolean").endObject()
                .startObject("age").field("type", "integer").endObject()
                .startObject("birthday").field("type", "date").field("format", "YYYYMMddhhMMSS").endObject()
                .endObject()
                .endObject()
                .endObject();
        cib.addMapping("prindextype", mapping);
        cib.execute().actionGet();
    }

    /**
     * 该方法为增加索引记录
     *
     * @throws Exception
     */
    public void buildIndex() throws Exception {
        // productindex_liwei为上个方法中定义的索引,prindextype为类型.jk8231为id,以此可以代替memchche来进行数据的缓存
        IndexResponse response = client.prepareIndex("productindex_liwei", "prindextype", null)
                .setSource(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("name", "sanwa")//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 age等
                                .field("home", "USA")
                                .field("hight", 232)
                                .field("iswoman", true)
                                .field("age", 2)
                                .field("birthday", Joda_Time.getNowDate())
                                .field("state", "默认属性,mapping中没有定义")//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.
                                .endObject()
                )
                .setTTL(8000)//这样就等于单独设定了该条记录的失效时间,单位是毫秒,必须在mapping中打开_ttl的设置开关
                .execute()
                .actionGet();

        IndexResponse response2 = client.prepareIndex("productindex_liwei", "prindextype")
                .setSource(XContentFactory.jsonBuilder()
                                .startObject()
                                .field("name", "xiaopang")
                                .field("home", "中国")
                                .field("hight", 232)
                                .field("iswoman", true)
                                .field("age", 22)
                                .field("birthday", Joda_Time.getNowDate())
                                .endObject()
                )
                .execute()
                .actionGet();

    }

    /**
     * @throws IOException
     */
    public void bulkApi() throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        // either use client#prepare, or use Requests# to directly build index/delete requests
        bulkRequest.add(client.prepareIndex("productindex_liwei", "prindextype")
                        .setSource(XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("name", "liw")//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 age等
                                        .field("home", "中国")
                                        .field("hight", 179.9)
                                        .field("iswoman", false)
                                        .field("age", 25)
                                        .field("birthday", Joda_Time.getNowDate())
                                        .field("state", "默认属性,mapping中没有定义")//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.
                                        .endObject()
                        )
        );

        bulkRequest.add(client.prepareIndex("productindex_liwei", "prindextype")
                        .setSource(XContentFactory.jsonBuilder()
                                        .startObject()
                                        .field("name", "jiaojiao")//该字段在上面的方法中mapping定义了,所以该字段就有了自定义的属性,比如 age等
                                        .field("home", "中国")
                                        .field("hight", 169.9)
                                        .field("iswoman", true)
                                        .field("age", 26)
                                        .field("birthday", Joda_Time.getNowDate())
                                        .field("state", "默认属性,mapping中没有定义")//该字段在上面方法中的mapping中没有定义,所以该字段的属性使用es默认的.
                                        .endObject()
                        )
        );

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        //如果失败
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }
    }

    /**
     * 搜索API
     */
    public void searchApi() {
        SearchResponse response = client.prepareSearch("productindex_liwei")
                //.setTypes("type1", "type2")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                //.setQuery(QueryBuilders.termQuery("multi", "test"))             // Query
                .setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))   // Filter
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();
        SearchHit[] searchHits = response.getHits().getHits();
        for (SearchHit searchHit : searchHits) {
            System.out.println("------------搜索出来的数据jsondata------------\n " + searchHit.getSourceAsString());
        }
    }

    /**
     * 搜索的使用
     */
    public void exm() {

        System.out.println("------------删除------------");
        DeleteResponse responsedd = client.prepareDelete("productindex_liwei", "prindextype", "jk8234")
                .setOperationThreaded(false)
                .execute()
                .actionGet();

        System.out.println("------------根据主键搜索得到值------------");
        GetResponse responsere = client.prepareGet("productindex_liwei", "prindextype", "jk8231")
                .execute()
                .actionGet();
        System.out.println("------------完成读取------------\n" + responsere.getSourceAsString());


        System.out.println("------------搜索------------");
        //搜索productindex_liwei,prepareSearch(String... indices)注意该方法的参数,可以搜索多个索引
        SearchRequestBuilder builder = client.prepareSearch("productindex_liwei")
                .setTypes("prindextype")
                .setSearchType(SearchType.DEFAULT)
                .setFrom(0)
                .setSize(50);
        QueryBuilder qb2 = QueryBuilders.boolQuery() // boolQuery() 就相当于 sql中的and
                .must(new QueryStringQueryBuilder("中国人3").field("home"))//QueryStringQueryBuilder是单个字段的搜索条件,相当于组织sql的  where后面的   字段名=字段值
                .should(new QueryStringQueryBuilder("3").field("dfsfs"))
                .must(QueryBuilders.termQuery("dfsfs", "中"));//关于QueryStringQueryBuilder及termQuery等的使用可以使用es插件head来进行操作体会个中query的不同
        builder.setQuery(qb2);
        SearchResponse responsesearch = builder.execute().actionGet();
        System.out.println("" + responsesearch);
        try {
            String jsondata = responsesearch.getHits().getHits()[0].getSourceAsString();
            System.out.println("------------搜索出来的数据jsondata------------\n " + jsondata);
        } catch (Exception es) {

        }
    }

    public static void main(String[] dfd) {
        Es_Demo esm = new Es_Demo();
        esm.setClient(esm.buildClient());
        try {
            //esm.buildIndexMapping();
            //
            //esm.buildIndex();
            //esm.bulkApi();
            //esm.exm();
            esm.searchApi();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            esm.shutDownClient();
        }
    }
}
