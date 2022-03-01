<template>
  <div>
    <v-card elevation="5" class="mb-2">
      <v-card-title>
        <v-icon class="mx-3">$password</v-icon>
        修改密码
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid" ref="pwdFrom">
          <v-container>
            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="pwd.oldPwd"
                  :rules="oldRules"
                  label="原密码"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="pwd.newPwd"
                  :rules="oldRules"
                  label="新密码"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="new2"
                  :rules="[newRules]"
                  label="确认新密码"
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
                  @click="savePwd()"
                  >保存</v-btn
                >
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { changePwd, getUser } from "@/api/park/user";
import { getUserInfo } from "@/utils/auth";
import { showSuccessMsg } from "@/utils/msg";

export default {
  inject: ["reload"], //注入App里的reload方法
  data: () => ({
    pwd: {
      id: null,
      oldPwd: null,
      encodePwd: null,
      newPwd: null,
    },
    valid: true,
    new2: null,
    oldRules: [
      (v) => !!v || "密码不能为空",
      (v) =>
        (v && /^[0-9a-zA-Z_]{1,}$/.test(v)) ||
        "密码格式不正确，只能由字母，数字，下划线组成",
      (v) => (v && v.length >= 6) || "密码至少6个",
    ],
  }),

  created() {
    //获取用户旧密码编码
    getUser(this.getUsername()).then((result) => {
      this.pwd.encodePwd = result.data.password;
      this.pwd.id = result.data.id;
    });
  },

  methods: {
    getUsername() {
      let userInfo = getUserInfo();
      return userInfo ? userInfo.username : null;
    },
    newRules() {
      //校验
      if (!/^[0-9a-zA-Z_]{1,}$/.test(this.new2)) {
        return "密码格式不正确，只能由字母，数字，下划线组成";
      } else if (!this.new2) {
        return "密码不能为空";
      } else if (this.new2.length < 6) {
        return "密码至少6个";
      } else if (this.pwd.newPwd != this.new2) {
        return "与新密码不一致";
      } else {
        return true;
      }
    },
    savePwd() {
      //保存按钮
      if (this.$refs.pwdFrom.validate()) {
        changePwd(this.pwd).then((result) => {
          showSuccessMsg(result.msg);
          if (result.code != 300) {
            //刷新本页
            //延迟0.5秒刷新 防止刷新过快
            setTimeout(() => {
              this.reload();
            }, 500);
          }
        });
      }
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
