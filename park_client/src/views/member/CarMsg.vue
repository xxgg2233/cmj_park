<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$car</v-icon>
        车辆信息
      </v-card-title>
      <v-card-text>
        <v-data-table
          fixed-header
          disable-filtering
          disable-pagination
          disable-sort
          height="400"
          show-select
          hide-default-footer
          v-model="selected"
          :headers="headers"
          :items="resultList"
        >
          <template v-slot:item.carPhoto="{ item }">
            <img contain:true width="100" :src="getPhoto(item)" />
          </template>

          <template v-slot:item.note="{ item }">
            {{ item.note == null ? "无" : item.note }}
          </template>

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
                  @keyup.enter="getPagedCar"
                  class="py-3"
                ></v-text-field>
              </v-responsive>
              <v-btn outlined color="primary" class="ml-3" @click="getPagedCar">
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
              <v-btn outlined color="orange" class="ml-5" @click="add()">
                <v-icon class="mr-1">$plus</v-icon>
                添加
              </v-btn>
              <v-btn outlined color="red" class="ml-3" @click="del()">
                <v-icon class="mr-1">$minus</v-icon>
                批量删除
              </v-btn>
            </v-toolbar>
          </template>
          <template v-slot:item.allowComment="{ item }">
            {{ item.allowComment ? "是" : "否" }}
          </template>
          <template v-slot:item.actions="{ item }">
            <v-btn text class="btn-dense" color="blue" @click="edit(item.id)"
              >编辑</v-btn
            >
            <v-btn text class="btn-dense" color="red" @click="del(item.id)"
              >删除</v-btn
            >
          </template>
        </v-data-table>
        <v-pagination
          v-model="page"
          class="my-4"
          :length="totalPages"
          :total-visible="10"
          @input="getPagedCar"
        ></v-pagination>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { deleteCar, listCar } from "@/api/park/car";
import { showSuccessMsg, showInfoMsg } from "@/utils/msg";

export default {
  name: "MemberCar",
  data: () => ({
    headers: [
      { text: "id", value: "id", class: "subtitle-1" },
      { text: "车牌号", value: "carNumber", class: "subtitle-1" },
      { text: "车辆品牌", value: "carName", class: "subtitle-1" },
      { text: "车辆照片", value: "carPhoto", class: "subtitle-1" },
      { text: "备注", value: "note", class: "subtitle-1" },
      { text: "操作", value: "actions", class: "subtitle-1" },
    ],
    selected: [],
    keyword: null,
    page: 1,
    totalPages: 0,
    pageSize: 10,
    resultList: [],
  }),
  created() {
    this.getPagedCar();
  },
  methods: {
    resetQuery() {
      this.keyword = null;
      this.page = 1;
      this.getPagedCar();
    },
    getPagedCar() {
      //获取车辆信息
      listCar({
        page: this.page,
        pageSize: this.pageSize,
        carNumber: this.keyword,
      }).then((result) => {
        this.resultList = result.data.list;
        this.page = result.data.pageNum;
        this.totalPages = result.data.pages;
      });
    },
    add() {
      //添加
      this.$router.push("/member/carEditor/0");
    },
    edit(carId) {
      //编辑
      this.$router.push("/member/carEditor/" + carId);
    },
    del(carId) {
      let ids = carId ? [carId] : this.selected.map((item) => item.id);
      if (ids.length === 0) return;
      let msg = carId
        ? "真的要删除该车辆信息吗？"
        : "真的要删除选中的车辆信息吗？";
      showInfoMsg(msg, this.deleteCar, ids);
    },
    deleteCar(ids) {
      //删除
      deleteCar(ids).then((result) => {
        showSuccessMsg(result.msg);
        this.getPagedCar();
      });
    },
    getPhoto(item) {
      try {
        return require("c:/upload/" + item.carPhoto);
      } catch (error) {
        return require("@/assets/error.png");
      }
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
