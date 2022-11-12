package com.planradar.weather.data.utils

object JsonReader {
    fun getJson(path: String): String ="{\n" +
            "  \"cod\": \"200\",\n" +
            "  \"message\": 0,\n" +
            "  \"cnt\": 40,\n" +
            "  \"list\": [ {\n" +
            "    \"dt\": 1666882800,\n" +
            "    \"main\": {\n" +
            "      \"temp\": 301.65,\n" +
            "      \"feels_like\": 301.43,\n" +
            "      \"temp_min\": 301.65,\n" +
            "      \"temp_max\": 301.82,\n" +
            "      \"pressure\": 1016,\n" +
            "      \"sea_level\": 1016,\n" +
            "      \"grnd_level\": 1013,\n" +
            "      \"humidity\": 42,\n" +
            "      \"temp_kf\": -0.17\n" +
            "    },\n" +
            "    \"weather\": [\n" +
            "      {\n" +
            "        \"id\": 803,\n" +
            "        \"main\": \"Clouds\",\n" +
            "        \"description\": \"broken clouds\",\n" +
            "        \"icon\": \"04d\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"clouds\": {\n" +
            "      \"all\": 52\n" +
            "    },\n" +
            "    \"wind\": {\n" +
            "      \"speed\": 4.95,\n" +
            "      \"deg\": 49,\n" +
            "      \"gust\": 5.73\n" +
            "    },\n" +
            "    \"visibility\": 10000,\n" +
            "    \"pop\": 0,\n" +
            "    \"sys\": {\n" +
            "      \"pod\": \"d\"\n" +
            "    },\n" +
            "    \"dt_txt\": \"2022-10-27 15:00:00\"\n" +
            "  }],\n" +
            "  \"city\": {\n" +
            "    \"id\": 360630,\n" +
            "    \"name\": \"Cairo\",\n" +
            "    \"coord\": {\n" +
            "      \"lat\": 30.0626,\n" +
            "      \"lon\": 31.2497\n" +
            "    },\n" +
            "    \"country\": \"EG\",\n" +
            "    \"population\": 7734614,\n" +
            "    \"timezone\": 7200,\n" +
            "    \"sunrise\": 1666843492,\n" +
            "    \"sunset\": 1666883561\n" +
            "  }\n" +
            "}"
}
