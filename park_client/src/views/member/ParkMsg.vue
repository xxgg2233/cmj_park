<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$park</v-icon>
        车位信息
      </v-card-title>
      <v-card-text>
        <v-data-table
          loading="true"
          loading-text="没有查到数据"
          fixed-header
          multi-sort
          disable-filtering
          disable-pagination
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
                  label="请输入车位或区域"
                  dense
                  hide-details
                  outlined
                  clearable
                  @keyup.enter="getPagedPark"
                  class="py-3"
                ></v-text-field>
              </v-responsive>
              <v-btn
                outlined
                color="primary"
                class="ml-3"
                @click="getPagedPark"
              >
                <v-icon class="mr-1">$search</v-icon>
                查询
              </v-btn>
              <v-btn
                outlined
                color="primary"
                class="ml-3"
                @click="getFreeParking"
              >
                <v-icon class="mr-1">$search</v-icon>
                查看空闲车位
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
          <template v-slot:item.allowComment="{ item }">
            {{ item.allowComment ? "是" : "否" }}
          </template>

          <template v-slot:item.carNumber="{ item }">
            {{ item.carNumber == null ? "空闲" : item.carNumber + "已预约" }}
          </template>

          <template v-slot:item.actions="props">
            <v-edit-dialog
              v-if="props.item.carNumber == null"
              :return-value.sync="props.item.actions"
              large
              save-text="预约车位"
              cancel-text="取消"
              @save="save(props.item.id)"
              @cancel="cancel"
            >
              <v-btn outlined color="primary"
                >{{ props.item.actions }}预约</v-btn
              >

              <template v-slot:input>
                <div class="mt-4 text-h6">预约车牌号</div>
                <v-select
                  no-data-text="请先添加车俩信息"
                  v-model="select"
                  :items="items"
                  return-object
                ></v-select>
              </template>
            </v-edit-dialog>
            <v-btn
              @click="noSave(props.item.id, props.item.carNumber)"
              outlined
              v-if="
                props.item.carNumber != null &&
                items.indexOf(props.item.carNumber) != -1
              "
              color="primary"
              >{{ props.item.actions }}取消预约</v-btn
            >
            <v-btn
              outlined
              disabled
              v-if="
                props.item.carNumber != null &&
                items.indexOf(props.item.carNumber) == -1
              "
              >{{ props.item.actions }}已被预约</v-btn
            >
          </template>
        </v-data-table>
        <v-pagination
          v-model="page"
          class="my-4"
          :length="totalPages"
          :total-visible="10"
          @input="getPagedPark"
        ></v-pagination>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { listPark, updateCarN } from "@/api/park/park";
import { listCar } from "@/api/park/car";
import { showSuccessMsg, showInfoMsg } from "@/utils/msg";

export default {
  inject: ["reload"], //注入App里的reload方法
  name: "MemberPark",
  data: () => ({
    headers: [
      { text: "id", value: "id", class: "subtitle-1" },
      { text: "车位号", value: "parkNumber", class: "subtitle-1" },
      { text: "区域", value: "parkArea", class: "subtitle-1" },
      { text: "每小时收费", value: "fee", class: "subtitle-1" },
      { text: "备注", value: "note", class: "subtitle-1" },
      { text: "状态", value: "carNumber", class: "subtitle-1" },
      { text: "操作", value: "actions", class: "subtitle-1" },
    ],
    items: [],
    select: null,
    keyword: null,
    page: 1,
    totalPages: 0,
    pageSize: 10,
    resultList: [],
  }),
  created() {
    this.getPagedPark();
    this.getcar();
  },
  methods: {
    resetQuery() {
      this.keyword = null;
      this.page = 1;
      this.getPagedPark();
    },
    getcar() {
      //获取车牌信息
      listCar({
        page: this.page,
        pageSize: this.pageSize,
        carNumber: this.keyword,
      }).then((result) => {
        //赋值到选项中
        result.data.list.forEach((item) => {
          this.items.push(item.carNumber);
        });
      });
    },
    getPagedPark() {
      //获取车场信息
      listPark({
        page: this.page,
        pageSize: this.pageSize,
        parkNumber: this.keyword,
      }).then((result) => {
        this.resultList = result.data.list;
        this.page = result.data.pageNum;
        this.totalPages = result.data.pages;
      });
    },
    getFreeParking() {
      //获取空闲车位
      listPark({
        page: this.page,
        pageSize: this.pageSize,
        carNumber: "nullCar",
      }).then((result) => {
        this.resultList = result.data.list;
        this.page = result.data.pageNum;
        this.totalPages = result.data.pages;
      });
    },
    save(id) {
      if (this.select != null) {
        updateCarN({
          id: id,
          note: 1,
          carNumber: this.select,
        }).then((result) => {
          showSuccessMsg(result.msg);
          this.reloadThis(result);
        });
      } else {
        showSuccessMsg("请选择或添加车辆信息！");
      }
    },
    noSave(id, cn) {
      updateCarN({
        //取消预约 note标识是预约还是取消
        id: id,
        note: "0",
        carNumber: cn,
      }).then((result) => {
        showSuccessMsg(result.msg);
        this.reloadThis(result);
      });
    },
    cancel() {},
    edit(carId) {
      //弹窗提示
      let id = carId;
      if (id.length === 0) return;
      let msg = "真的要预定该车位吗？";
      showInfoMsg(msg, this.updatePark, id);
    },
    updatePark(id) {
      //修改车位信息
      updatePark(id).then((result) => {
        showSuccessMsg(result.msg);
        this.getPagedPark();
      });
    },
    reloadThis(result) {
      if (result.code != 500) {
        //刷新本页
        //延迟0.5秒刷新 防止刷新过快
        setTimeout(() => {
          this.reload();
        }, 500);
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
