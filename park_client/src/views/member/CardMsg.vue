<template>
  <div>
    <v-card elevation="5" class="mb-2">
      <v-card-title>
        <v-icon class="mx-3">$card</v-icon>
        卡余额
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid" ref="UserFrom">
          <v-container>
            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="card.number"
                  label="卡号"
                  outlined
                  dense
                  disabled
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="card.money"
                  label="余额"
                  outlined
                  dense
                  disabled
                ></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { getCardMsg } from "@/api/park/user";
import { getUserInfo } from "@/utils/auth";
export default {
  data: () => ({
    card: {
      bumber: null,
      money: null,
    },
    valid: true,
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
      getCardMsg(this.getUsername()).then((result) => {
        (this.card.number = result.data.cardNumber),
          (this.card.money = result.data.cardMoney);
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
