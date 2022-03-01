<template>
  <div>
    <v-card elevation="5" class="mb-2">
      <v-card-title>
        <v-icon class="mx-3">$set</v-icon>
        公告设置
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid" ref="annonForm">
          <v-container>
            <v-row dense>
              <v-col cols="12">
                <v-text-field
                  v-model="aCopy.title"
                  label="公告标题（必填）"
                  outlined
                  :rules="titleRules"
                  dense
                ></v-text-field>
              </v-col>
            </v-row>
            <v-row dense>
              <v-col cols="12">
                <mavon-editor
                  placeholder="公告内容（必填）"
                  v-model="aCopy.content"
                  :autofocus="false"
                />
                <div style="height: 12px" class="body-2 red--text ml-2 mt-1">
                  {{ validContent ? "" : "内容最少10个字符" }}
                </div>
              </v-col>
            </v-row>
            <v-row dense class="mt-4 mb-2">
              <v-col cols="12" class="text-center">
                <v-btn
                  color="primary"
                  dark
                  small
                  rounded
                  class="mr-4"
                  @click="saveAnnoun()"
                  >保存</v-btn
                >
                <v-btn color="orange" dark small rounded @click="resetForm()"
                  >重置</v-btn
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
import { getAnnounAdmin, updateAnnoun } from "@/api/park/admin/announ";
import { showSuccessMsg } from "@/utils/msg";

export default {
  data: () => ({
    aCopy: {},
    valid: true,
    validContent: true,
    titleRules: [
      (v) => !!v || "标题不能为空",
      (v) => (v && v.length >= 6) || "标题最少6个字符",
    ],
  }),
  created() {
    this.getAnnoun();
  },
  methods: {
    getAnnoun() {
      getAnnounAdmin().then((result) => {
        this.article = result.data;
        // 表单操作的是副本数据，便于恢复初始数据
        this.aCopy = Object.assign({}, this.article);
      });
    },
    saveAnnoun() {
      this.$refs.annonForm.validate();
      this.checkContent();
      if (this.valid && this.validContent) {
        updateAnnoun(this.aCopy).then((result) => {
          showSuccessMsg(result.msg);
          this.article = Object.assign({}, this.aCopy);
        });
      }
    },
    resetForm() {
      this.aCopy = Object.assign({}, this.article);
      this.validContent = true;
      this.$refs.annonForm.resetValidation();
    },
    checkContent(value) {
      if (value === undefined) {
        // 非mavon-editor触发
        this.validContent =
          this.aCopy.content && this.aCopy.content.length >= 10;
      } else {
        this.validContent = value && value.length >= 10;
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
