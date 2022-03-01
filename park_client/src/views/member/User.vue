<template>
  <div>
    <v-card elevation="5" class="mb-2">
      <v-card-title>
        <v-icon class="mx-3">$user</v-icon>
        用户信息
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid" ref="UserFrom">
          <v-container>
            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="udate.username"
                  label="用户名"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="udate.realname"
                  label="真实姓名"
                  :rules="nameRules"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="udate.phone"
                  label="电话"
                  outlined
                  :rules="phoneRules"
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="udate.email"
                  label="邮箱(选填)"
                  :rules="emailRules"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-btn
                  color="primary"
                  dark
                  small
                  rounded
                  class="mr-4"
                  @click="saveUser()"
                  >保存</v-btn
                >
              </v-col>
            </v-row>
            <template v-slot:item.allowComment="{ item }">
              {{ item.allowComment ? "是" : "否" }}
            </template>
          </v-container>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { getUser, updateUser } from "@/api/park/user";
import { getUserInfo, removeUserInfo, removeToken } from "@/utils/auth";
import { showSuccessMsg, showInfoMsg } from "@/utils/msg";
import { logout } from "@/api/park/login";

export default {
  data: () => ({
    udate: {
      id: null,
      username: null,
      email: null,
      realname: null,
      phone: null,
    },
    valid: true,

    emailRules: [
      (v) =>
        v == null ||
        /^$|^\w{1,64}@[a-z0-9\-]{1,256}(\.[a-z]{2,6}){1,2}$/i.test(v) ||
        "邮箱格式不对",
    ],
    nameRules: [
      (v) => !!v || "真实姓名不能为空",
      (v) =>
        (v && /^([\u4e00-\u9fa5]{1,15}|[a-zA-Z\.\s]{1,20})$/i.test(v)) ||
        "请填写正确姓名",
    ],
    phoneRules: [
      (v) => !!v || "电话不能为空",
      (v) =>
        (v &&
          /^([1]\d{10}|([\(（]?0[0-9]{2,3}[）\)]?[-]?)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?)$/i.test(
            v
          )) ||
        "请填写正确电话",
    ],
  }),
  created() {
    this.getUser();
  },

  methods: {
    getUsername() {
      let userInfo = getUserInfo();
      return userInfo ? userInfo.username : null;
    },
    getUser() {
      getUser(this.getUsername()).then((result) => {
        (this.udate.id = result.data.id),
          (this.udate.username = result.data.username),
          (this.udate.email = result.data.email),
          (this.udate.realname = result.data.realname),
          (this.udate.phone = result.data.phone);
      });
    },
    saveUser() {
      this.$refs.UserFrom.validate();
      if (this.valid) {
        let msg = "修改成功后将重新登录，是否修改？";
        showInfoMsg(msg, this.updateUser);
      }
    },
    updateUser() {
      updateUser(this.udate).then((result) => {
        if (result.code != 500) {
          showSuccessMsg(result.msg + "，将退出重新登录！");
          setTimeout(() => {
            //需要延迟的代码 :3秒后延迟跳转到首页，可以加提示什么的
            logout().then((result) => {
              showSuccessMsg(result.msg);

              removeToken();
              removeUserInfo();
              this.$router.push("/Login");
            });
          }, 1500);
        }
      });
    },
  },
};
</script>


<style lang="scss" scoped>
// 取消mavon-editor设置的z轴高度
.v-note-wrapper {
  z-index: inherit;
}
</style>
