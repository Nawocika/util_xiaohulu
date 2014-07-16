package com.elasticsearch;

/**
 * Created by lw on 14-7-15.
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 官方JAVA-API
 * http://www.elasticsearch.org/guide/en/elasticsearch/client/java-api/current/index.html
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
public class Es_Test {

    public static void main(String[] dfd) {
        Es_Utils.startupClient();
        try {
            Es_BuildIndex.buildIndexMapping();
            //buildIndex(User.getOneRandomUser());
            Es_BuildIndex.buildBulkIndex(User.getRandomUsers(1000));
            //searchById("5_XDJBA-TBOra4a7oyiYrA");
            //searchByQuery();
            //searchByQuery_Count();
            //updateByQuery();
            //Es_Facets.searchByQuery_Facets();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Es_Utils.shutDownClient();
        }
    }
}