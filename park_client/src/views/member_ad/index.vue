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
              src="@/assets/admin.jpg"
              max-width="30"
              max-height="30"
            ></v-img>
          </v-list-item-avatar>
          <v-list-item-title>{{ getUsername() }}</v-list-item-title>
        </v-list-item>

        <v-divider></v-divider>
        <v-list-item>
          <v-list-item-title>角色管理</v-list-item-title>
        </v-list-item>

        <v-list-item-group>
          <v-list-item dense to="/admin/adminMsg">
            <v-list-item-icon>
              <v-icon>$admin</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>管理员管理</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/admin/userMsg">
            <v-list-item-icon>
              <v-icon>$user</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>用户管理</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>

        <v-divider></v-divider>
        <v-list-item>
          <v-list-item-title>系统使用</v-list-item-title>
        </v-list-item>
        <v-list-item-group>
          <v-list-item dense to="/admin/carPortMsg">
            <v-list-item-icon>
              <v-icon>$park</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>车位管理</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/admin/payFee">
            <v-list-item-icon>
              <v-icon>$pay</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>停车缴费</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/admin/announcement">
            <v-list-item-icon>
              <v-icon>$set</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>公告设置</v-list-item-title>
            </v-list-item-content>
          </v-list-item>

          <v-list-item dense to="/admin/allRecord">
            <v-list-item-icon>
              <v-icon>$history</v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title>停车记录查看</v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list-item-group>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-title>管理中心</v-app-bar-title>
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
import { getAdminInfo, removeAdminInfo, removeTokenAdmin } from "@/utils/auth";
import { logout } from "@/api/park/login";
import { showSuccessMsg } from "@/utils/msg";

export default {
  name: "Member",
  data: () => ({ drawer: null }),
  methods: {
    getUsername() {
      let userInfo = getAdminInfo();
      return userInfo ? userInfo.username : null;
    },
    logout() {
      logout().then((result) => {
        showSuccessMsg(result.msg);

        removeAdminInfo();
        removeTokenAdmin();
        this.$router.push("/login/admins");
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
