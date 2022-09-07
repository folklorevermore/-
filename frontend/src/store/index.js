import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import { Message } from "element-ui"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user_info: [],
    car_info: [],
    record_info: [],
    currentplate: '',
    currentcid: '',
    plateexist: 0,
    isparkexist: 0,
    inTime: 0,
    outTime: 0,
    sevendaycount: {
      dimensions: {
        name: '7天',
        data: ['', '', '', '', '', '', ''],
      },
      measures: [{
        name: '停车',
        data: [0, 0, 0, 0, 0, 0, 0]
      }, {
        name: '出场',
        data: [0, 0, 0, 0, 0, 0, 0]
      }]
    },
    coutreadytoshow: false,
  },
  mutations: {
    //登录功能
    checkuserpassword(state, obj) {
      axios.get('/api/user/findByUid', {
        params: {
          uid: obj.uid
        }
      })
        .then(res => {
          if (res.data.data.password == obj.password) {
            sessionStorage.setItem('username', res.data.data.userName);
            Message.success('登录成功');
            if (res.data.data.role == 1) {
              sessionStorage.setItem('isloged', '1');
              sessionStorage.setItem('menunumber', '0')
              location.reload();
            } else if (res.data.data.role == 0) {
              sessionStorage.setItem('isloged', '0');
              location.reload();
            }
          } else {
            Message.error('密码错误');
          }
        })
        .catch(err => {
          Message.error('账号不存在');
          console.error(err);
        })
    },
    logout() {
      sessionStorage.clear();
      location.reload();
    },


    //停车管理员功能
    checkplatein(state, obj) {
      axios.get('/api//record/findByPlate', {
        params: {
          plate: obj
        }
      })
        .then(res => {
          var stopstate = 0;
          for (var i = 0; i < res.data.data.length; i++) {
            if (res.data.data[i].outTime == 0) {
              Message.error('车辆已入场，请勿重复添加！');
              stopstate = 1;
            }
          }
          if (stopstate == 0) {
            axios.get('/api/car/findByPlate', {
              params: {
                plate: obj
              }
            })
              .then(res => {
                console.log(res.data)
                if (res.data.code == 200) {
                  if (res.data.data.carport) {
                    let date = new Date();  //当前标准时间格式
                    let timestamp = parseInt(new Date().getTime() / 1000);
                    let year = date.getFullYear(); //取得四位数的年份
                    let month = date.getMonth() + 1; //返回0~11之间的数字，0代表一月，11代表12月
                    let day = date.getDate(); //返回天数，0~31，getDay()返回的是星期几（0~6）
                    let hour = date.getHours(); //获取小时
                    let minute = date.getMinutes(); //获取分钟
                    let second = date.getSeconds(); //获取秒
                    if (month < 10)
                      month = '0' + month
                    if (day < 10)
                      day = '0' + day
                    if (hour < 10)
                      hour = '0' + hour
                    if (minute < 10)
                      minute = '0' + minute
                    if (second < 10)
                      second = '0' + second
                    let num = year + "" + month + "" + day + "" + hour + "" + minute + "" + second;
                    console.log(timestamp);
                    axios.get('/api/record/add', {
                      params: {
                        rid: num,
                        cid: res.data.data.id,
                        type: 1,
                        inTime: timestamp,
                        outTime: 0,
                        charge: 0,
                      }
                    })
                      .then(res => {
                        console.log(res);
                        Message.success('业主停车成功！');
                      })
                      .catch(err => {
                        console.error(err);
                        Message.error('系统错误，停车失败！');
                      })
                  } else if (res.data.data.carport == "") {
                    let date = new Date();  //当前标准时间格式
                    let timestamp = parseInt(new Date().getTime() / 1000);
                    let year = date.getFullYear(); //取得四位数的年份
                    let month = date.getMonth() + 1; //返回0~11之间的数字，0代表一月，11代表12月
                    let day = date.getDate(); //返回天数，0~31，getDay()返回的是星期几（0~6）
                    let hour = date.getHours(); //获取小时
                    let minute = date.getMinutes(); //获取分钟
                    let second = date.getSeconds(); //获取秒
                    if (month < 10)
                      month = '0' + month
                    if (day < 10)
                      day = '0' + day
                    if (hour < 10)
                      hour = '0' + hour
                    if (minute < 10)
                      minute = '0' + minute
                    if (second < 10)
                      second = '0' + second
                    let num = year + "" + month + "" + day + "" + hour + "" + minute + "" + second;
                    console.log(timestamp);
                    axios.get('/api/record/add', {
                      params: {
                        rid: num,
                        cid: res.data.data.id,
                        type: 0,
                        inTime: timestamp,
                        outTime: 0,
                        charge: 0,
                      }
                    })
                      .then(res => {
                        console.log(res);
                        Message.success('停车成功，临时停车计费已开始！');
                      })
                      .catch(err => {
                        console.error(err);
                        Message.error('系统错误，停车失败！');
                      })
                  }
                } else {
                  Message.warning('车辆信息不存在，请先录入车辆信息！');
                  state.currentplate = obj;
                  state.plateexist += 1;
                }
              })
          }
          console.log(res)
        })
        .catch(err => {
          console.error(err);
        })
        .catch(err => {
          console.error(err);
        })
    },
    addoutcar(state, obj) {
      axios.get('/api/car/add', {
        params: {
          plate: state.currentplate,
          carport: '',
          type: 0,
          ownerName: obj.name,
          ownerPhone: obj.phone,
        }
      })
        .then(res => {
          console.log(res);
          axios.get('/api/car/findByPlate', {
            params: {
              plate: state.currentplate,
            }
          })
            .then(res => {
              // console.log(res)
              let date = new Date();  //当前标准时间格式
              let timestamp = parseInt(new Date().getTime() / 1000);
              let year = date.getFullYear(); //取得四位数的年份
              let month = date.getMonth() + 1; //返回0~11之间的数字，0代表一月，11代表12月
              let day = date.getDate(); //返回天数，0~31，getDay()返回的是星期几（0~6）
              let hour = date.getHours(); //获取小时
              let minute = date.getMinutes(); //获取分钟
              let second = date.getSeconds(); //获取秒
              if (month < 10)
                month = '0' + month
              if (day < 10)
                day = '0' + day
              if (hour < 10)
                hour = '0' + hour
              if (minute < 10)
                minute = '0' + minute
              if (second < 10)
                second = '0' + second
              let num = year + "" + month + "" + day + "" + hour + "" + minute + "" + second;
              // console.log(timestamp);
              axios.get('/api/record/add', {
                params: {
                  rid: num,
                  cid: res.data.data.id,
                  type: 0,
                  inTime: timestamp,
                  outTime: 0,
                  charge: 0,
                }
              }).then(res => {
                console.log(res);
                Message.success('车辆信息入库成功！临时停车计费已开始！');
              })
                .catch(err => {
                  console.error(err);
                  Message.error('系统错误，停车失败！');
                })
              setTimeout("location.reload()", 3000);
            })
            .catch(err => {
              console.error(err);
            })
        })
        .catch(err => {
          Message.success('系统错误，信息添加失败！');
          console.error(err);
        })
    },
    outcar(state, obj) {
      axios.get("/api/record/findByPlate", {
        params: {
          plate: obj
        }
      })
        .then(res => {
          state.isparkexist = 0
          for (var i = 0; i < res.data.data.length; i++) {
            if (res.data.data[i].outTime == 0) {
              state.isparkexist = 1
              if (res.data.data[i].type == 1) {
                let timestamp = parseInt(new Date().getTime() / 1000);
                axios.get("/api/record/update", {
                  params: {
                    id: res.data.data[i].id,
                    rid: res.data.data[i].rid,
                    cid: res.data.data[i].cid,
                    type: res.data.data[i].type,
                    inTime: res.data.data[i].inTime,
                    outTime: timestamp,
                    charge: 0,
                  }
                })
                  .then(res => {
                    console.log(res);
                    Message.success('出场成功！');
                  })
                  .catch(err => {
                    console.error(err);
                    Message.error('系统错误，出场失败！');
                  })
              } else if (res.data.data[i].type == 0) {
                let timestamp = parseInt(new Date().getTime() / 1000);
                state.inTime = res.data.data[i].inTime;
                state.outTime = timestamp;
                let cash = (((state.outTime - state.inTime) / 3600).toFixed()) * 5
                axios.get("/api/record/update", {
                  params: {
                    id: res.data.data[i].id,
                    rid: res.data.data[i].rid,
                    cid: res.data.data[i].cid,
                    type: res.data.data[i].type,
                    inTime: res.data.data[i].inTime,
                    outTime: timestamp,
                    charge: cash,
                  }
                })
                  .then(res => {
                    console.log(res);
                    Message.warning({
                      duration: 0,
                      showClose: true,
                      message: '临时停车结束，请缴费' + cash + '元。'
                    });
                  })
                  .catch(err => {
                    console.error(err);
                    Message.error('系统错误，出场失败！');
                  })
              }
            }
          }
          if (state.isparkexist == 0) {
            Message.error('该车辆未停车，请检查你的输入！')
          }
          console.log(res.data.data)
        })
        .catch(err => {
          Message.error('该车辆未停车，请检查你的输入！')
          console.error(err);
        })
    },


    //系统管理员-用户
    getuserinfo(state) {
      axios.get('/api/user/findAll')
        .then(res => {
          console.log(res)
          state.user_info = res.data.data
          for (var i = 0; i < state.user_info.length; i++) {
            if (state.user_info[i].role == 1) {
              state.user_info[i].role = '系统管理员';
            } else if (state.user_info[i].role == 0) {
              state.user_info[i].role = '停车管理员';
            }
          }
        })
        .catch(err => {
          console.error(err);
          Message.error('查询错误！请联系系统管理员');
        })
    },
    updateuserinfo(state, obj) {
      axios.get('/api/user/update', {
        params: {
          id: obj.id,
          uid: obj.uid,
          userName: obj.username,
          password: obj.password,
        }
      })
        .then(res => {
          Message.success('修改成功！');
          location.reload();
          console.log(res);
        })
        .catch(err => {
          Message.error('系统错误，修改失败！');
          console.error(err);
        })
    },
    adduser(state, obj) {
      axios.get('/api/user/add', {
        params: {
          uid: obj.uid,
          userName: obj.username,
          password: obj.password,
        }
      })
        .then(res => {
          console.log(res);
          Message.success('添加用户成功！');
          location.reload();
        })
        .catch(err => {
          console.error(err);
          Message.error('系统错误，添加用户失败！');
        })
    },
    searchuserbyaccount(state, obj) {
      axios.get('/api/user/findByUid', {
        params: {
          uid: obj
        }
      })
        .then(res => {
          state.user_info = [{ id: res.data.data.id, password: res.data.data.password, role: res.data.data.role, uid: res.data.data.uid, userName: res.data.data.userName }]
          console.log(res)
        })
        .catch(err => {
          console.error(err);
        })
    },
    searchuserbyname(state, obj) {
      axios.get('/api/user/findByUserName', {
        params: {
          userName: obj
        }
      })
        .then(res => {
          state.user_info = res.data.data
          console.log(res)
        })
        .catch(err => {
          console.error(err);
        })
    },


    //系统管理员-车
    getcarinfo(state) {
      axios.get('/api/car/findAll')
        .then(res => {
          state.car_info = res.data.data
          for (var i = 0; i < res.data.data.length; i++) {
            if (res.data.data[i].type == 0) {
              state.car_info[i].type = '无车位'
            } else if (res.data.data[i].type == 1) {
              state.car_info[i].type = '有车位'
            }
          }
        })
        .catch(err => {
          console.error(err);
        })
    },
    updatecarinfo(state, obj) {
      axios.get('/api/car/update', {
        params: {
          id: obj.id,
          plate: obj.plate,
          carport: obj.carport,
          type: obj.type,
          ownerName: obj.name,
          ownerPhone: obj.phone,
        }
      })
        .then(res => {
          console.log(res);
          Message.success('修改成功');
          location.reload();
        })
        .catch(err => {
          console.error(err);
          Message.error('系统错误，修改失败！');
        })
    },
    addcar(state, obj) {
      axios.get('/api/car/add', {
        params: {
          plate: obj.plate,
          carport: obj.carport,
          type: obj.type,
          ownerName: obj.name,
          ownerPhone: obj.phone,
        }
      })
        .then(res => {
          console.log(res);
          Message.success('添加车辆成功');
          location.reload();
        })
        .catch(err => {
          console.error(err);
          Message.error('系统错误，添加车辆失败！');
        })
    },
    searchbyplate(state, obj) {
      axios.get('/api/car/findByPlate', {
        params: {
          plate: obj
        }
      })
        .then(res => {
          state.car_info = [{ carport: res.data.data.carport, id: res.data.data.id, ownerName: res.data.data.ownerName, ownerPhone: res.data.data.ownerPhone, plate: res.data.data.plate, type: res.data.data.type }]
          Message.success('查询成功')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },
    searchbycarport(state, obj) {
      axios.get('/api/car/findByCarport', {
        params: {
          carport: obj
        }
      })
        .then(res => {
          state.car_info = [{ carport: res.data.data.carport, id: res.data.data.id, ownerName: res.data.data.ownerName, ownerPhone: res.data.data.ownerPhone, plate: res.data.data.plate, type: res.data.data.type }]
          Message.success('查询成功！')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },
    searchbyownername(state, obj) {
      axios.get('/api/car/findByOwnerName', {
        params: {
          ownerName: obj
        }
      })
        .then(res => {
          state.car_info = res.data.data
          Message.success('查询成功')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },
    searchbyownerphone(state, obj) {
      axios.get('/api/car/findByOwnerPhone', {
        params: {
          ownerPhone: obj
        }
      })
        .then(res => {
          state.car_info = res.data.data
          Message.success('查询成功！')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },


    //系统管理员-记录
    getrecordinfo(state) {
      axios.get("/api/record/findAll")
        .then(res => {
          state.record_info = res.data.data
          for (var i = 0; i < res.data.data.length; i++) {
            if (res.data.data[i].type == 0) {
              state.record_info[i].type = '临时停车'
            } else if (res.data.data[i].type == 1) {
              state.record_info[i].type = '免费'
            }
            res.data.data[i].charge += '元';
          }
          console.log(res)
        })
        .catch(err => {
          console.error(err);
        })
    },
    updaterecordinfo(state, obj) {
      axios.get('/api/car/findByPlate', {
        params: {
          plate: obj.plate
        }
      })
        .then(res => {
          state.currentcid = res.data.data.id
          console.log(res)
          setTimeout(function () {
            axios.get("/api/record/update", {
              params: {
                id: obj.id,
                rid: obj.rid,
                cid: state.currentcid,
                type: obj.type,
                charge: obj.charge,
                inTime: obj.intime / 1000,
                outTime: obj.outtime / 1000,
              }
            })
              .then(res => {
                console.log(res);
                Message.success('修改成功');
                location.reload()
              })
              .catch(err => {
                console.error(err);
                Message.error('系统错误，修改失败！');
              })
          }, 1000);
        })
        .catch(err => {
          console.error(err);
          Message.error('此车信息未入库，请先入库后再操作！')
        })
    },
    addrecord(state, obj) {
      axios.get('/api/car/findByPlate', {
        params: {
          plate: obj.plate
        }
      })
        .then(res => {
          state.currentcid = res.data.data.id
          console.log(res)
          setTimeout(function () {
            axios.get("/api/record/add", {
              params: {
                rid: obj.rid,
                cid: state.currentcid,
                type: obj.type,
                inTime: obj.intime / 1000,
                outTime: obj.outtime / 1000,
                charge: obj.charge,
              }
            })
              .then(res => {
                console.log(res);
                Message.success('新增成功！');
                location.reload()
              })
              .catch(err => {
                console.error(err);
                Message.error('系统错误，新增失败！');
              })
          }, 1000);
        })
        .catch(err => {
          console.error(err);
          Message.error('此车信息未入库，请先入库后再操作！')
        })
    },
    searchrecordbyplate(state, obj) {
      axios.get("/api/record/findByPlate", {
        params: {
          plate: obj,
        }
      })
        .then(res => {
          state.record_info = res.data.data
          Message.success('查询成功！')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },
    searchrecordbyrid(state, obj) {
      axios.get("/api/record/findByRid", {
        params: {
          rid: obj,
        }
      })
        .then(res => {
          state.record_info = [{ id: res.data.data.id, rid: res.data.data.rid, cid: res.data.data.cid, type: res.data.data.type, inTime: res.data.data.inTime, outTime: res.data.data.outTime, car: { id: res.data.data.car.id, plate: res.data.data.car.plate, carport: res.data.data.car.carport, type: res.data.data.car.type, ownerName: res.data.data.car.ownerName, ownerPhone: res.data.data.car.ownerPhone } }]
          Message.success('查询成功！')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },
    searchrecordbycarport(state, obj) {
      axios.get("/api/record/findByCarport", {
        params: {
          carport: obj,
        }
      })
        .then(res => {
          state.record_info = res.data.data
          Message.success('查询成功！')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },
    searchrecordbytype(state, obj) {
      axios.get("/api/record/findByType", {
        params: {
          type: obj,
        }
      })
        .then(res => {
          state.record_info = res.data.data
          Message.success('查询成功！')
          console.log(res)
        })
        .catch(err => {
          Message.warning('检索的信息不存在！')
          console.error(err);
        })
    },

    
    //获取7天统计数据
    get7daycountdata(state) {
      //timestamp：当天的开始的零时零点的时间戳
      var timestamp = new Date().setHours(0, 0, 0, 0);

      //hover预览详情
      for (var a = 0; a < 7; a++) {
        let time = new Date(new Date().setHours(0, 0, 0, 0) - 86400000 * a);
        const m = (time.getMonth() + 1 + "").padStart(2, "0");
        const d = (time.getDate() + "").padStart(2, "0");
        state.sevendaycount.dimensions.data[a] = m + '月' + d + '日';
      }

      axios.get('/api/record/findAll')
        .then(res => {
          //处理inTime入场数据
          for (var i = 0; i < res.data.data.length; i++) {
            if ((timestamp / 1000) < res.data.data[i].inTime && res.data.data[i].inTime <= ((timestamp + 86400000) / 1000)) {
              //当天
              state.sevendaycount.measures[0].data[0] += 1
            } else if ((timestamp - 86400000) / 1000 < res.data.data[i].inTime) {
              //昨天
              state.sevendaycount.measures[0].data[1] += 1
            } else if ((timestamp - 86400000 * 2) / 1000 < res.data.data[i].inTime) {
              //前天
              state.sevendaycount.measures[0].data[2] += 1
            } else if ((timestamp - 86400000 * 3) / 1000 < res.data.data[i].inTime) {
              state.sevendaycount.measures[0].data[3] += 1
            } else if ((timestamp - 86400000 * 4) / 1000 < res.data.data[i].inTime) {
              state.sevendaycount.measures[0].data[4] += 1
            } else if ((timestamp - 86400000 * 5) / 1000 < res.data.data[i].inTime) {
              state.sevendaycount.measures[0].data[5] += 1
            } else if ((timestamp - 86400000 * 6) / 1000 < res.data.data[i].inTime) {
              state.sevendaycount.measures[0].data[6] += 1
            }
          }
          //处理outTime出场数据
          for (var j = 0; j < res.data.data.length; j++) {
            if ((timestamp / 1000) < res.data.data[j].outTime && res.data.data[j].outTime <= ((timestamp + 86400000) / 1000)) {
              //当天
              state.sevendaycount.measures[1].data[0] += 1
            } else if ((timestamp - 86400000) / 1000 < res.data.data[j].outTime) {
              //昨天
              state.sevendaycount.measures[1].data[1] += 1
            } else if ((timestamp - 86400000 * 2) / 1000 < res.data.data[j].outTime) {
              //前天
              state.sevendaycount.measures[1].data[2] += 1
            } else if ((timestamp - 86400000 * 3) / 1000 < res.data.data[j].outTime) {
              state.sevendaycount.measures[1].data[3] += 1
            } else if ((timestamp - 86400000 * 4) / 1000 < res.data.data[j].outTime) {
              state.sevendaycount.measures[1].data[4] += 1
            } else if ((timestamp - 86400000 * 5) / 1000 < res.data.data[j].outTime) {
              state.sevendaycount.measures[1].data[5] += 1
            } else if ((timestamp - 86400000 * 6) / 1000 < res.data.data[j].outTime) {
              state.sevendaycount.measures[1].data[6] += 1
            }
          }
          state.coutreadytoshow = true
        })
        .catch(err => {
          console.error(err);
        })









      // const m = (timestamp.getMonth() + 1 + "").padStart(2, "0");
      // const d = (timestamp.getDate() + "").padStart(2, "0");
      // console.log(d);
      // return `${m}-${d}`;
    }
  }
})
