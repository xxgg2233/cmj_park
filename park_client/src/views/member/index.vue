<template>
  <v-app id="member">
    <v-navigation-drawer
      v-model="drawer"
      app
      permanent
      expand-on-hover
      color="blue-grey lighten-4"
    >
      <v-list>
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img
              src="@/assets/user.jpg"
              max-width="30"
              max-height="30"
            ></v-img>
          </v-list-item-avatar>
          <v-list-item-title>{{ getUsername() }}</v-list-item-title>
        </v-list-item>

        <v-divider></v-divider>
        <v-list-item>
          <v-list-item-title>个人信息管理</v-list-item-title>
        </v-list-item>

        <v-list-item-group>
          <v-list-item dense to="/member/user">
            <v-list-item-icon>
              <v-icon>$user</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>修改个人信息</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/member/changePwd">
            <v-list-item-icon>
              <v-icon>$password</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>密码修改</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>

        <v-divider></v-divider>
        <v-list-item>
          <v-list-item-title>系统使用</v-list-item-title>
        </v-list-item>
        <v-list-item-group>
          <v-list-item dense to="/member/carMsg">
            <v-list-item-icon>
              <v-icon>$car</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>车辆信息</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/member/cardmsg">
            <v-list-item-icon>
              <v-icon>$card</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>卡余额</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/member/ParkMsg">
            <v-list-item-icon>
              <v-icon>$park</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>车位信息</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/member/Record">
            <v-list-item-icon>
              <v-icon>$history</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>停车记录查看</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/member/Announ">
            <v-list-item-icon>
              <v-icon>$note</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>停车须知</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-title>会员中心</v-app-bar-title>
      <v-btn
        text
        color="black"
        class="ml-3 btn-dense"
        @click="$router.push('/')"
        >查看公告</v-btn
      >
      <v-btn text color="orange" class="btn-dense" @click="logout"
        >退出登录</v-btn
      >
    </v-app-bar>

    <v-main>
      <v-container class="py-6 px-4" fluid>
        <router-view />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import { getUserInfo, removeUserInfo, removeToken } from "@/utils/auth";
import { logout } from "@/api/park/login";
import { showSuccessMsg } from "@/utils/msg";

export default {
  name: "Member",
  data: () => ({ drawer: null }),
  methods: {
    getUsername() {
      let userInfo = getUserInfo();
      return userInfo ? userInfo.username : null;
    },
    logout() {
      logout().then((result) => {
        showSuccessMsg(result.msg);

        removeUserInfo();
        removeToken();
        this.$router.push("/login");
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
