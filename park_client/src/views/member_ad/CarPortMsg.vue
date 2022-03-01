<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$park</v-icon>
        车位管理
      </v-card-title>
      <v-card-text>
        <v-data-table
          fixed-header
          disable-filtering
          disable-pagination
          multi-sort
          height="400"
          show-select
          hide-default-footer
          v-model="selected"
          :headers="headers"
          :items="resultList"
        >
          <template v-slot:top>
            <v-toolbar flat>
              <v-responsive max-width="260">
                <v-text-field
                  v-model="keyword"
                  label="车位或区域"
                  dense
                  hide-details
                  outlined
                  clearable
                  @keyup.enter="getPark"
                  class="py-3"
                ></v-text-field>
              </v-responsive>
              <v-btn outlined color="primary" class="ml-3" @click="getPark">
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

              <v-dialog v-model="dialog" max-width="500px">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    outlined
                    color="orange"
                    class="ml-5"
                    @click="add()"
                    v-bind="attrs"
                    v-on="on"
                  >
                    <v-icon class="mr-1">$plus</v-icon>
                    添加
                  </v-btn>
                </template>

                <v-card>
                  <v-card-title>车位信息</v-card-title>
                  <v-card-text>
                    <v-form v-model="valid" ref="parkForm">
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.parkNumber"
                              label="车位号"
                              :rules="nullRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.parkArea"
                              label="区域"
                              :rules="nullRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.fee"
                              label="每小时收费"
                              :rules="feeRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.note"
                              label="备注"
                            ></v-text-field>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-form>
                  </v-card-text>

                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="close()">
                      关闭
                    </v-btn>
                    <v-btn color="blue darken-1" text @click="save()">
                      保存
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>

              <v-btn outlined color="red" class="ml-3" @click="del()">
                <v-icon class="mr-1">$minus</v-icon>
                批量删除
              </v-btn>
            </v-toolbar>
          </template>

          <template v-slot:item.fee="{ item }">
            {{ item.fee + "元/小时" }}
          </template>

          <template v-slot:item.allowComment="{ item }">
            {{ item.allowComment ? "是" : "否" }}
          </template>
          <template v-slot:item.actions="{ item }">
            <v-btn text class="btn-dense" color="blue" @click="edit(item)"
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
          @input="getPark"
        ></v-pagination>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import {
  deletePark,
  listPark,
  addPark,
  updatePark,
} from "@/api/park/admin/carPortMsg";
import { showSuccessMsg, showInfoMsg } from "@/utils/msg";

export default {
  inject: ["reload"], //注入App里的reload方法
  name: "Member_ad_Admin",
  data: () => ({
    headers: [
      { text: "id", value: "id", class: "subtitle-1" },
      { text: "车位号", value: "parkNumber", class: "subtitle-1" },
      { text: "区域", value: "parkArea", class: "subtitle-1" },
      { text: "每小时收费(元)", value: "fee", class: "subtitle-1" },
      { text: "备注", value: "note", class: "subtitle-1" },
      { text: "操作", value: "actions", class: "subtitle-1" },
    ],
    dialog: false,
    selected: [],
    keyword: null,
    page: 1,
    totalPages: 0,
    pageSize: 10,
    flag: null,
    result: {},
    resultList: [],
    valid: true,
    nullRules: [(v) => !!v || "信息不能为空"],
    feeRules: [
      (v) => !!v || "金额不能为空",
      (v) => (v && /^(0|-?[1-9][0-9]*)(\.\d)?$/i.test(v)) || "请填写正确金额",
    ],
  }),
  created() {
    this.getPark();
  },
  methods: {
    resetQuery() {
      this.keyword = null;
      this.page = 1;
      this.getPark();
    },
    add() {
      //设置辨别，区分添加还是编辑
      this.flag = "add";
    },
    getPark() {
      //获取车位信息
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
    close() {
      //关闭
      this.dialog = false;
      this.result.id = 0;
      this.reload();
    },
    save() {
      //保存
      this.$refs.parkForm.validate();
      if (this.valid) {
        if (this.flag == "add") {
          addPark({
            parkNumber: this.result.parkNumber,
            parkArea: this.result.parkArea,
            fee: this.result.fee,
            note: this.result.note,
          }).then((result) => {
            showSuccessMsg(result.msg);
            this.reloadThis(result);
          });
        } else {
          updatePark({
            id: this.result.id,
            parkNumber: this.result.parkNumber,
            parkArea: this.result.parkArea,
            fee: this.result.fee,
            note: this.result.note,
          }).then((result) => {
            showSuccessMsg(result.msg);
            this.reloadThis(result);
          });
        }
      }
    },
    edit(item) {
      //编辑
      this.flag = "edit";
      this.dialog = true;
      this.result = item;
    },
    del(parkId) {
      let ids = parkId ? [parkId] : this.selected.map((item) => item.id);
      if (ids.length === 0) return;
      let msg = parkId ? "真的要删除该车位吗？" : "真的要删除选中的车位吗？";
      showInfoMsg(msg, this.deletePark, ids);
    },
    deletePark(ids) {
      deletePark(ids).then((result) => {
        showSuccessMsg(result.msg);
        this.getPark();
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
