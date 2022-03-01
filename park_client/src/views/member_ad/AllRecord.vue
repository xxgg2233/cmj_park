<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$history</v-icon>
        停车记录
      </v-card-title>
      <v-card-text>
        <v-data-table
          fixed-header
          disable-filtering
          disable-pagination
          multi-sort
          height="400"
          hide-default-footer
          :headers="headers"
          :items="resultList"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-responsive max-width="260">
                <v-text-field
                  v-model="keyword"
                  label="车牌号"
                  dense
                  hide-details
                  outlined
                  clearable
                  @keyup.enter="getAllRecord"
                  class="py-3"
                ></v-text-field>
              </v-responsive>
              <v-btn
                outlined
                color="primary"
                class="ml-3"
                @click="getAllRecord"
              >
                <v-icon class="mr-1">$search</v-icon>
                查询
              </v-btn>
              <v-btn
                outlined
                color="primary"
                class="ml-3"
                @click="resetQuery()"
              >
                <v-icon class="mr-1">$refresh</v-icon>
                重置
              </v-btn>
            </v-toolbar>
          </template>
          <template v-slot:item.carStart="{ item }">
            {{ item.carStart.replace("T", " ") }}
          </template>
          <template v-slot:item.carEnd="{ item }">
            {{ item.carEnd.replace("T", " ") }}
          </template>
          <template v-slot:item.hourMoney="{ item }">
            {{ item.hourMoney + "元" }}
          </template>
          <template v-slot:item.time="{ item }">
            {{ item.time + "分钟" }}
          </template>
          <template v-slot:item.money="{ item }">
            {{ item.money + "元" }}
          </template>
        </v-data-table>
        <v-pagination
          v-model="page"
          class="my-4"
          :length="totalPages"
          :total-visible="10"
          @input="getAllRecord"
        ></v-pagination>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { listAllRec } from "@/api/park/record";

export default {
  name: "AllRecord",
  data: () => ({
    headers: [
      { text: "id", value: "id", class: "subtitle-1" },
      { text: "车牌号", value: "carNumber", class: "subtitle-1" },
      { text: "停车开始时间", value: "carStart", class: "subtitle-1" },
      { text: "停车结束时间", value: "carEnd", class: "subtitle-1" },
      { text: "每小时收费", value: "hourMoney", class: "subtitle-1" },
      { text: "停车时长(分钟)", value: "time", class: "subtitle-1" },
      { text: "收费金额", value: "money", class: "subtitle-1" },
      { text: "停车用户", value: "owner", class: "subtitle-1" },
    ],
    keyword: null,
    page: 1,
    totalPages: 0,
    pageSize: 10,
    resultList: [],
  }),
  created() {
    this.getAllRecord();
  },
  methods: {
    resetQuery() {
      this.keyword = null;
      this.page = 1;
      this.getAllRecord();
    },
    getAllRecord() {
      listAllRec({
        page: this.page,
        pageSize: this.pageSize,
        carNumber: this.keyword,
      }).then((result) => {
        this.resultList = result.data.list;
        this.page = result.data.pageNum;
        this.totalPages = result.data.pages;
      });
    },
  },
};
</script>


<style lang="scss" scoped>
.btn-dense {
  height: 28px !important;
  min-width: 36px !important;
  padding: 0 8px !important;
}
</style>
