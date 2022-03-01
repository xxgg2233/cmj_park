<template>
  <div>
    <v-card elevation="5" class="mb-2">
      <v-card-title>
        <v-icon class="mx-3">$note</v-icon>
        停车公告
      </v-card-title>
      <v-card-text>
        <v-form>
          <v-container>
            <v-row dense>
              <v-col cols="12">
                <v-text-field
                  class="changeColor"
                  disabled
                  v-model="aCopy.title"
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row dense>
              <v-col cols="12">
                <v-textarea
                  class="changeColor"
                  disabled
                  auto-grow
                  outlined
                  v-model="aCopy.content"
                />
              </v-col>
            </v-row>
          </v-container>
        </v-form>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { getAnnoun } from "@/api/park/admin/announ";

export default {
  data: () => ({
    aCopy: {},
  }),
  created() {
    this.getAnnoun();
  },
  methods: {
    getAnnoun() {
      getAnnoun().then((result) => {
        this.article = result.data;
        // 表单操作的是副本数据，便于恢复初始数据
        this.aCopy = Object.assign({}, this.article);
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
.changeColor {
  -webkit-text-fill-color: #333;
}
</style>
