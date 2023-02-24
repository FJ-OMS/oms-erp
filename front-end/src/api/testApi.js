/**
 * 示例
 * 作者：匿名用户
                            _ooOoo_
                           o8888888o
                           88" . "88
                           (| -_- |)
                            O\ = /O
                        ____/`---'\____
                      .   ' \\| | `.
                       / \\||| : ||| \
                     / _||||| -:- |||||- \
                       | | \\\ - / | |
                     | \_| ''\---/'' | |
                      \ .-\__ `-` ___/-. /
                   ___`. .' /--.--\ `. . __
                ."" '< `.___\_<|>_/___.' >'"".
               | | : `- \`.;`\ _ /`;.`/ - ` : | |
                 \ \ `-. \_ __\ /__ _/ .-` / /
         ======`-.____`-.___\_____/___.-`____.-'======
                            `=---='

         .............................................
                  佛祖保佑             永无BUG
          佛曰:
                  写字楼里写字间，写字间里程序员；
                  程序人员写程序，又拿程序换酒钱。
                  酒醒只在网上坐，酒醉还来网下眠；
                  酒醉酒醒日复日，网上网下年复年。
                  但愿老死电脑间，不愿鞠躬老板前；
                  奔驰宝马贵者趣，公交自行程序员。
                  别人笑我忒疯癫，我笑自己命太贱；
                  不见满街漂亮妹，哪个归得程序员？

 */

/**
 * @description API 配置
 * config.prefix                : API url前缀
 * config.items[n].key          : 别名，调用此接口时用到，如   testApi.transactionList(API_DATA).then(res=>{})
 * config.items[n].url          : API url
 *                                如果链接以 / 结尾，兼容rest风格（请求参数拼接在路径上）
 *                                带{key}花括号 ， 把value = data[key] 替换到url上{key}里，并删除data.key
 * config.items[n].method:
 * config.items[n].type         : 强制传参方式:query /  body; 为空，按method方式对应默认：get=>query,其它=>body
 * config.items[n].loadingHide  : 请求不显示loading动画; 为空，显示loading
 *
 * 特殊：（在url的{}替换后） 如果存在data.BjBody ， 则执行 data=data.BjBody
 */
import { generate } from '@/utils/request';
const config = {
  prefix: '',
  items: [
    /**
     * 示例
     *  @organizationId : 0
     */
    {
      key: 'transactionList',
      url: '/iam/skyer/v1/{organizationId}/users/paging',
      method: 'get',
      type: 'query',
      loadingHide: true,
    },
    {
      key: 'getBillJdExcel',
      url: '/simp/v1/0/template/SFIN.BILL_JD/excel',
      method: 'get',
      type: 'query',
      loadingHide: true,
      rType: 2,
    },
  ],
};

export default {
  ...generate(config),
};
