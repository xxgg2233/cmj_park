<template>
  <div>
    <v-card elevation="5">
      <v-card-title>
        <v-icon class="mx-3">$admin</v-icon>
        管理员管理
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
                  label="管理员名字"
                  dense
                  hide-details
                  outlined
                  clearable
                  @keyup.enter="getAdmin"
                  class="py-3"
                ></v-text-field>
              </v-responsive>
              <v-btn outlined color="primary" class="ml-3" @click="getAdmin">
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
                  <v-card-title>管理员信息</v-card-title>
                  <v-card-text>
                    <v-form v-model="valid" ref="adminForm">
                      <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                            <v-text-field
                              v-model="result.name"
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
          @input="getAdmin"
        ></v-pagination>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import {
  deleteAdmin,
  listAdmin,
  addAdmin,
  updateAdmin,
} from "@/api/park/admin/adminMsg";
import { showSuccessMsg, showInfoMsg } from "@/utils/msg";

export default {
  inject: ["reload"], //注入App里的reload方法
  name: "Member_ad_Admin",
  data: () => ({
    headers: [
      { text: "id", value: "id", class: "subtitle-1" },
      { text: "用户名", value: "name", class: "subtitle-1" },
      { text: "真实姓名", value: "realName", class: "subtitle-1" },
      { text: "手机号", value: "phone", class: "subtitle-1" },
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
  }),
  created() {
    this.getAdmin();
  },
  methods: {
    resetQuery() {
      this.keyword = null;
      this.page = 1;
      this.getAdmin();
    },
    add() {
      this.flag = "add";
    },
    getAdmin() {
      listAdmin({
        page: this.page,
        pageSize: this.pageSize,
        name: this.keyword,
      }).then((result) => {
        this.resultList = result.data.list;
        this.page = result.data.pageNum;
        this.totalPages = result.data.pages;
      });
    },
    close() {
      this.dialog = false;
      this.result.id = 0;
      this.reload();
    },
    save() {
      this.$refs.adminForm.validate();
      if (this.valid) {
        if (this.flag == "add") {
          addAdmin({
            name: this.result.name,
            password: this.result.pwd,
            realName: this.result.realName,
            phone: this.result.phone,
          }).then((result) => {
            showSuccessMsg(result.msg);
          });
        } else {
          updateAdmin({
            id: this.result.id,
            name: this.result.name,
            password: this.result.pwd,
            realName: this.result.realName,
            phone: this.result.phone,
          }).then((result) => {
            showSuccessMsg(result.msg);
          });
        }
        //延迟0.5秒刷新 防止刷新过快
        setTimeout(() => {
          this.reload();
        }, 500);
      }
    },
    edit(item) {
      this.flag = "edit";
      this.dialog = true;
      this.result = item;
    },
    del(adminId) {
      let ids = adminId ? [adminId] : this.selected.map((item) => item.id);
      if (ids.length === 0) return;
      let msg = adminId
        ? "真的要删除该管理员吗？"
        : "真的要删除选中的管理员吗？";
      showInfoMsg(msg, this.deleteAdmin, ids);
    },
    deleteAdmin(ids) {
      deleteAdmin(ids).then((result) => {
        showSuccessMsg(result.msg);
        this.getAdmin();
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
