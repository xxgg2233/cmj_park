<template>
  <v-app>
    <v-main class="login">
      <v-container fill-height>
        <v-row align="center" justify="center">
          <v-col lg="5" md="6" sm="7">
            <v-card>
              <v-toolbar color="cyan" dark dense>
                <v-icon>$register</v-icon>
                <v-toolbar-title class="ml-2">用户注册</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form v-model="valid" ref="registerForm">
                  <v-container fluid>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-text-field
                          v-model="registerData.username"
                          label="用户名"
                          counter="50"
                          hint="3-50个字符"
                          clearable
                          outlined
                          color="cyan lighten-1"
                          :rules="usernameRules"
                          type="text"
                          dense
                          prepend-icon="$user"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-text-field
                          v-model="registerData.realname"
                          label="真实姓名"
                          :rules="nameRules"
                          counter="50"
                          hint="3-50个字符"
                          clearable
                          outlined
                          color="cyan lighten-1"
                          type="text"
                          dense
                          prepend-icon="$realname"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-text-field
                          v-model="registerData.password"
                          label="密码"
                          counter="10"
                          hint="6-10个字符"
                          clearable
                          outlined
                          color="cyan lighten-1"
                          :rules="passwordRules"
                          type="password"
                          dense
                          prepend-icon="$password"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-text-field
                          v-model="registerData.phone"
                          label="手机"
                          counter="50"
                          hint="手机号长度为11个字符"
                          clearable
                          outlined
                          color="cyan lighten-1"
                          :rules="phoneRules"
                          type="text"
                          dense
                          prepend-icon="$phone"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-text-field
                          v-model="registerData.email"
                          label="邮箱(选填)"
                          counter="50"
                          hint="邮箱，3-50个字符"
                          clearable
                          outlined
                          color="cyan lighten-1"
                          :rules="emailRules"
                          type="text"
                          dense
                          prepend-icon="$email"
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-divider> </v-divider>
                      </v-col>
                    </v-row>

                    <a
                      style="display: block; text-align: center"
                      @click="goLogin()"
                      >已有账号？点击登录</a
                    >

                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-divider> </v-divider>
                      </v-col>
                    </v-row>
                    <div class="d-flex justify-center mt-3">
                      <v-btn
                        small
                        depressed
                        rounded
                        min-width="200"
                        color="cyan lighten-1"
                        class="mr-4"
                        dark
                        @click="register()"
                      >
                        注册
                      </v-btn>
                    </div>
                  </v-container>
                </v-form>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { addUser } from "@/api/park/register";
import { showSuccessMsg } from "@/utils/msg";

export default {
  data: () => ({
    fromPath: null,
    registerData: {
      id: null,
      username: null,
      realname: null,
      password: null,
      phone: null,
      email: null,
    },
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
  }),
  created() {
    if (this.$route.query.fromPath) this.fromPath = this.$route.query.fromPath;
  },
  methods: {
    register() {
      this.$refs.registerForm.validate();
      if (this.valid) {
        addUser(this.registerData).then((result) => {
          showSuccessMsg(result.msg);
          if (result.msg == "注册成功") {
            showSuccessMsg("正在跳转登录页面！");
            setTimeout(() => {
              this.$router.push("/Login");
            }, 2000);
          }
        });
      }
    },
    goLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style lang="scss">
.login {
  background: url("../assets/img/login_background.jpg") no-repeat;
  background-size: cover;
}
</style>
