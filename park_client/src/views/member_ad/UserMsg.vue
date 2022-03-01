<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$user</v-icon>
        用户管理
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
          <template v-slot:top>
            <v-toolbar flat>
              <v-responsive max-width="260">
                <v-text-field
                  v-model="keyword"
                  label="用户名字"
                  dense
                  hide-details
                  outlined
                  clearable
                  @keyup.enter="getUser"
                  class="py-3"
                ></v-text-field>
              </v-responsive>
              <v-btn outlined color="primary" class="ml-3" @click="getUser">
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
                  <v-card-title>
                    <span class="text-h5">用户信息</span>
                  </v-card-title>
                  <v-card-text>
                    <v-form v-model="valid" ref="userForm">
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.username"
                              label="用户名"
                              :rules="usernameRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.pwd"
                              label="密码"
                              :rules="passwordRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.realName"
                              label="真实姓名"
                              :rules="nameRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.phone"
                              label="手机号"
                              :rules="phoneRules"
                            ></v-text-field>
                          </v-col>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.email"
                              label="邮箱(选填)"
                              :rules="emailRules"
                              required="false"
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

          <template v-slot:item.email="{ item }">
            {{ item.email == null || item.email == "" ? "无" : item.email }}
          </template>

          <template v-slot:item.cardNumber="{ item }">
            {{
              item.cardNumber == null || item.cardNumber == ""
                ? "无"
                : item.cardNumber
            }}
          </template>

          <template v-slot:item.allowComment="{ item }">
            {{ item.allowComment ? "是" : "否" }}
          </template>
          <template v-slot:item.actions="{ item }">
            <v-btn text class="btn-dense" color="blue" @click="edit(item)"
              >编辑</v-btn
            >
            <v-dialog v-model="ch" persistent max-width="290">
              <template v-slot:activator="{ on, attrs }">
                <v-btn
                  text
                  class="btn-dense"
                  color="blue"
                  v-bind="attrs"
                  v-on="on"
                  @click="getname(item.username)"
                  v-if="item.cardNumber != null"
                  >充值
                </v-btn>
              </template>
              <v-card>
                <v-card-title class="text-h5"> 充值金额 </v-card-title>
                <v-col>
                  <v-text-field
                    :rules="numberRules"
                    v-model="result.cardMoney"
                    label="请输入金额（元）"
                  ></v-text-field>
                </v-col>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="close()">
                    取消
                  </v-btn>
                  <v-btn color="green darken-1" text @click="charge()">
                    确认
                  </v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>

            <v-btn
              text
              class="btn-dense"
              color="blue"
              v-if="item.cardNumber == null"
              @click="addCard1(item.username)"
              >添加缴费卡</v-btn
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
          @input="getUser"
        ></v-pagination>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import {
  deleteUser,
  listUser,
  addUser,
  updateUser,
  addCard,
  updateCard,
} from "@/api/park/admin/userMsg";
import { showSuccessMsg, showInfoMsg } from "@/utils/msg";

export default {
  inject: ["reload"], //注入App里的reload方法
  name: "Member_ad_User",
  data: () => ({
    headers: [
      { text: "id", value: "id", class: "subtitle-1" },
      { text: "用户名", value: "username", class: "subtitle-1" },
      { text: "真实姓名", value: "realName", class: "subtitle-1" },
      { text: "手机号", value: "phone", class: "subtitle-1" },
      { text: "邮箱", value: "email", class: "subtitle-1" },
      { text: "缴费卡号", value: "cardNumber", class: "subtitle-1" },
      { text: "余额", value: "cardMoney", class: "subtitle-1" },
      { text: "操作", value: "actions", class: "subtitle-1" },
    ],
    dialog: false,
    ch: false,
    name: null,
    selected: [],
    keyword: null,
    page: 1,
    totalPages: 0,
    pageSize: 10,
    flag: null,
    result: {},
    resultList: [],
    valid: true,
    usernameRules: [
      (v) => !!v || "用户名不能为空",
      (v) => (v && v.length >= 3 && v.length <= 30) || "用户名：3-30个字符",
    ],
    nameRules: [
      (v) => !!v || "真实姓名不能为空",
      (v) =>
        (v && /^([\u4e00-\u9fa5]{1,15}|[a-zA-Z\.\s]{1,20})$/i.test(v)) ||
        "请填写正确姓名",
    ],
    passwordRules: [
      (v) => !!v || "密码不能为空",
      (v) =>
        (v && /^[0-9a-zA-Z_]{1,}$/.test(v)) ||
        "密码格式不正确，只能由字母，数字，下划线组成",
      (v) => (v && v.length >= 6 && v.length <= 10) || "密码：6-10个字符",
    ],
    phoneRules: [
      (v) => !!v || "手机号不能为空",
      (v) => (v && /^[1]([3-9])[0-9]{9}$/.test(v)) || "手机号格式不对",
      (v) => (v && v.length == 11) || "手机号长度不对",
    ],
    emailRules: [
      (v) =>
        v == null ||
        /^$|^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/i.test(v) ||
        "邮箱格式不对",
    ],
    numberRules: [
      (v) => !!v || "金额不能为空",
      (v) =>
        (v &&
          /(^0\.[1-9]\d?$)|(^0\.\d[1-9]?$)|(^[1-9]\d{0,7}(\.\d{0,2})?$)/i.test(
            v
          )) ||
        "请输入正确的金额格式",
    ],
  }),
  created() {
    this.getUser();
  },
  methods: {
    resetQuery() {
      this.keyword = null;
      this.page = 1;
      this.getUser();
    },
    add() {
      //用户判断是添加还是编辑
      this.flag = "add";
    },
    getname(msg) {
      this.name = msg;
    },
    getUser() {
      //获取用户信息
      listUser({
        page: this.page,
        pageSize: this.pageSize,
        username: this.keyword,
      }).then((result) => {
        this.resultList = result.data.list;
        this.page = result.data.pageNum;
        this.totalPages = result.data.pages;
      });
    },
    close() {
      //关闭按钮
      this.dialog = false;
      this.ch = false;
      this.result.id = 0;
      this.reload();
    },
    save() {
      //保存按钮
      this.$refs.userForm.validate();
      if (this.valid) {
        //规则校验是否通过
        if (this.flag == "add") {
          addUser({
            //添加用户
            username: this.result.username,
            password: this.result.pwd,
            realName: this.result.realName,
            phone: this.result.phone,
            email: this.result.email,
          }).then((result) => {
            showSuccessMsg(result.msg);
            this.reloadThis(result);
          });
        } else {
          updateUser({
            //更新用户
            id: this.result.id,
            username: this.result.username,
            password: this.result.pwd,
            realName: this.result.realName,
            phone: this.result.phone,
            email: this.result.email,
          }).then((result) => {
            showSuccessMsg(result.msg);
            this.reloadThis(result);
          });
        }
      }
    },
    charge() {
      //充值
      updateCard({
        username: this.name,
        cardMoney: this.result.cardMoney,
      }).then((result) => {
        showSuccessMsg(result.msg);
        this.reloadThis(result);
      });
    },
    addCard1(msg) {
      //添加缴费卡
      addCard({ username: msg }).then((result) => {
        showSuccessMsg(result.msg);
        this.reloadThis(result);
      });
    },
    edit(item) {
      this.flag = "edit";
      this.dialog = true;
      this.result = item;
    },
    del(userId) {
      //删除
      let ids = userId ? [userId] : this.selected.map((item) => item.id);
      if (ids.length === 0) return;
      let msg = userId ? "真的要删除该用户吗？" : "真的要删除选中的用户吗？";
      showInfoMsg(msg, this.deleteUser, ids);
    },
    deleteUser(ids) {
      deleteUser(ids).then((result) => {
        showSuccessMsg(result.msg);
        this.getUser();
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
