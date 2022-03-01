<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$pay</v-icon>
        停车缴费
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
            </v-toolbar>
          </template>

          <template v-slot:item.fee="{ item }">
            {{ item.fee + "元/小时" }}
          </template>

          <template v-slot:item.actions="{ item }">
            <v-dialog persistent v-model="dialog" max-width="500px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  text
                  class="btn-dense"
                  color="blue"
                  v-bind="attrs"
                  v-on="on"
                  @click="msg(item)"
                  v-if="item.carNumber != null"
                  >{{ item.carNumber + " 缴费" }}</v-btn
                >
              </template>
              <v-card>
                <v-card-title>
                  <span class="text-h5">缴费结算</span>
                </v-card-title>
                <v-card-text>
                  <v-form v-model="valid" ref="recordForm">
                    <v-container>
                      <v-row>
                        <v-col cols="8">
                          <v-text-field
                            prefix="车牌："
                            v-model="resultInput.carNumber"
                            :rules="carRules"
                          ></v-text-field>
                        </v-col>
                      </v-row>
                      <v-row>
                        <v-col cols="8">
                          <v-text-field
                            prefix="每小时收费："
                            v-model="resultInput.hourMoney"
                            suffix="（元/小时）"
                            :rules="feeRules"
                          ></v-text-field>
                        </v-col> </v-row
                      ><v-row>
                        <v-col cols="auto">
                          <v-text-field
                            prefix="停车时间："
                            type="datetime-local"
                            v-model="resultInput.carStart"
                            :rules="nullRules"
                          ></v-text-field>
                        </v-col> </v-row
                      ><v-row>
                        <v-col cols="auto">
                          <v-text-field
                            prefix="离开时间："
                            type="datetime-local"
                            v-model="resultInput.carEnd"
                            :rules="nullRules"
                          ></v-text-field>
                        </v-col> </v-row
                      ><v-row>
                        <v-col cols="8">
                          <v-text-field
                            prefix="停车时长："
                            v-model="resultInput.time"
                            suffix="（分钟）"
                            :rules="hourRules"
                            >{{
                              (resultInput.time = towMin(
                                (new Date(resultInput.carEnd) -
                                  new Date(resultInput.carStart)) /
                                  60000
                              ))
                            }}</v-text-field
                          >
                        </v-col> </v-row
                      ><v-row>
                        <v-col cols="8">
                          <v-text-field
                            prefix="收费金额："
                            v-model="resultInput.money"
                            suffix="（元）"
                            :rules="feeRules"
                            >{{
                              (resultInput.money = towMin(
                                ((Math.ceil(resultInput.time / 15) * 15) / 60) *
                                  resultInput.hourMoney
                              ))
                            }}</v-text-field
                          >
                          <span v-model="balance"
                            >该用户卡内余额：{{ balance }}(元)</span
                          >
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
                    提交
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <span v-if="item.carNumber == null">空闲</span>
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
import { listPark } from "@/api/park/admin/carPortMsg";
import { payFee, getMoney } from "@/api/park/admin/payFee";
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
    balance: null,
    resultInput: {
      id: null,
    },
    resultList: [],
    valid: true,
    nullRules: [(v) => !!v || "信息不能为空"],
    hourRules: [
      (v) => !!v || "时长不能为空",
      (v) =>
        (v && /(^[0-9]\d{0,7}(\.\d{0,2})?$)/i.test(v)) ||
        "请输入正确的时长格式",
    ],
    feeRules: [
      (v) => !!v || "金额不能为空",
      (v) =>
        (v && /(^[0-9]\d{0,7}(\.\d{0,2})?$)/i.test(v)) ||
        "请输入正确的金额格式",
    ],
    carRules: [
      (v) => !!v || "车牌号不能为空",
      (v) =>
        (v &&
          /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/.test(
            v
          )) ||
        "车牌格式不正确，请重新输入",
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
      //关闭按钮
      this.dialog = false;
      this.reload();
    },
    save() {
      //保存按钮
      this.$refs.recordForm.validate();
      if (this.valid) {
        //规则校验是否通过
        payFee(this.resultInput).then((result) => {
          showSuccessMsg(result.msg);
          this.reloadThis(result);
        });
      }
    },
    msg(item) {
      //停车缴费
      this.resultInput.id = item.id;
      this.resultInput.carNumber = item.carNumber;
      this.resultInput.hourMoney = item.fee;

      //获取用户的余额
      getMoney(item.carNumber).then((result) => {
        this.balance = result.data;
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
    towMin(val) {
      if (isNaN(val)) {
        return "";
      } else {
        return val.toFixed(2);
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
