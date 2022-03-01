<template>
  <v-app>
    <v-main class="login">
      <v-container fill-height>
        <v-row align="center" justify="center">
          <v-col lg="5" md="6" sm="7">
            <v-card>
              <v-toolbar color="cyan" dark dense>
                <v-icon>$login</v-icon>
                <v-toolbar-title class="ml-2">管理员登录</v-toolbar-title>
              </v-toolbar>
              <v-card-text>
                <v-form v-model="valid" ref="loginForm">
                  <v-container fluid>
                    <v-row justify="center" dense>
                      <v-col cols="11">
                        <v-text-field
                          v-model="loginData.username"
                          label="管理员名"
                          counter="50"
                          hint="邮箱/手机号，3-50个字符"
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
                          v-model="loginData.password"
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

                    <div class="d-flex justify-center mt-3">
                      <v-btn
                        small
                        depressed
                        rounded
                        min-width="60"
                        color="cyan lighten-1"
                        class="mr-4"
                        dark
                        @click="login()"
                      >
                        登录
                      </v-btn>
                      <v-btn
                        small
                        depressed
                        rounded
                        min-width="60"
                        color="deep-orange lighten-2"
                        dark
                        @click="$refs.loginForm.reset()"
                      >
                        重置
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
import { getLoggedInUserInfo, loginadmin } from "@/api/park/login";
import { setTokenAdmin, setAdminInfo } from "@/utils/auth";

export default {
  name: "Login",
  data: () => ({
    fromPath: null,
    loginData: {
      rememberMe: 0,
      username: null,
      password: null,
    },
    valid: true,
    usernameRules: [
      (v) => !!v || "用户名不能为空",
      (v) => (v && v.length >= 3 && v.length <= 30) || "用户名：3-30个字符",
    ],
    passwordRules: [
      (v) => !!v || "密码不能为空",
      (v) => (v && v.length >= 6 && v.length <= 10) || "密码：6-10个字符",
    ],
  }),
  created() {
    if (this.$route.query.fromPath) this.fromPath = this.$route.query.fromPath;
  },
  methods: {
    login() {
      this.$refs.loginForm.validate();
      if (this.valid) {
        loginadmin(this.loginData).then((result) => {
          // 保存jwt token
          setTokenAdmin(result.token);
          // 获取登录用户信息并保存
          getLoggedInUserInfo().then((result) => {
            setAdminInfo(result.userInfo);
            if (this.fromPath) {
              this.$router.push(this.fromPath);
            } else {
              this.$router.push("/admin");
            }
          });
        });
      }
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
