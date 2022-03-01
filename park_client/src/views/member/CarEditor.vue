<template>
  <div>
    <v-card elevation="5" class="mb-2">
      <v-card-title>
        <v-icon class="mx-3">$edit</v-icon>
        {{ action }}车辆信息
      </v-card-title>
      <v-card-text>
        <v-form v-model="valid" ref="carForm">
          <v-container>
            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="carCopy.carNumber"
                  label="车牌号码"
                  outlined
                  :rules="carRules"
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="carCopy.carName"
                  label="车辆品牌"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-file-input
                  v-model="Photo"
                  :rules="PhotoRules"
                  accept="image/png, image/jpeg, image/bmp"
                  prepend-icon="mdi-camera"
                  show-size
                  dense
                  outlined
                  label="车辆照片"
                ></v-file-input>
              </v-col>
            </v-row>

            <v-row dense>
              <v-col lg="6" cols="12">
                <v-text-field
                  v-model="carCopy.note"
                  label="备注(选填)"
                  outlined
                  dense
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row dense class="mt-4 mb-2">
              <v-col lg="6" cols="12" class="text-center">
                <v-btn
                  color="primary"
                  dark
                  small
                  rounded
                  class="mr-4"
                  @click="saveCar()"
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
import { addCar, getCarDetail, updateCar } from "@/api/park/car";
import { showSuccessMsg } from "@/utils/msg";

export default {
  name: "CarEditor",
  props: ["carId"],
  data: () => ({
    article: {},
    carCopy: {},
    Photo: null,
    valid: true,
    validContent: true,
    carRules: [
      (v) => !!v || "车牌号不能为空",
      (v) =>
        (v &&
          /^(([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z](([0-9]{5}[DF])|([DF]([A-HJ-NP-Z0-9])[0-9]{4})))|([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳使领]))$/.test(
            v
          )) ||
        "车牌格式不正确，请重新输入",
    ],
    PhotoRules: [
      (v) => !!v || "文件必选",
      (v) =>
        (v && /\.(bmp|jpg|gif|jpeg|png)$/i.test(v.name)) ||
        "请选择jpg,jpeg,bmp,png格式的图片",
      (v) => !v || v.size < 1024 * 1024 * 1 || "请上传小于1MB的照片!",
    ],
  }),
  created() {
    if (this.carId > 0) {
      // 编辑
      this.getCarDetail();
    }
  },
  computed: {
    action() {
      return this.carId > 0 ? "编辑" : "添加";
    },
  },
  methods: {
    getCarDetail() {
      getCarDetail(this.carId).then((result) => {
        this.article = result.data;
        // 表单操作的是副本数据，便于恢复初始数据
        this.carCopy = Object.assign({}, this.article);
      });
    },
    imgAdd() {
      //添加照片和表单信息
      let formdata = new FormData();
      formdata.append("file", this.Photo);
      formdata.append(
        "car",
        new Blob([JSON.stringify(this.carCopy)], { type: "application/json" })
      );
      return formdata;
    },
    updateAdd() {
      if (this.carId > 0) {
        //判断是编辑还是添加（编辑）
        updateCar(this.imgAdd()).then((result) => {
          //显示操作信息
          showSuccessMsg(result.msg);
          //副本数据覆盖
          this.article = Object.assign({}, this.carCopy);
        });
      } else {
        //添加
        addCar(this.imgAdd()).then((result) => {
          showSuccessMsg(result.msg);
          this.resetForm();
        });
      }
    },
    saveCar() {
      //保存事件
      //验证输入是否正确
      if (this.$refs.carForm.validate()) {
        this.updateAdd();
      }
    },
    resetForm() {
      //重置 覆盖副本数据
      this.carCopy = Object.assign({}, this.article);
      this.validContent = true;
      this.$refs.carForm.resetValidation();
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
