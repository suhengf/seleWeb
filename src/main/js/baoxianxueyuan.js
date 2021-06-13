// ==UserScript==
// @name         国开大学-保险学院-自动学习脚本
// @namespace    自动化脚本
// @version      0.32.2
// @description  hold the world!
// @author       凌峰

// @match        https://www.oucbx.com/OUCWEB/LEAP/Web/html/mystudy.html*

// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/courseDetails.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwVideoPlay.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwPDF.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/pdf/web/viewer.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwExercise.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwExerciseResult.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwExercisePaser.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwQa.html*
// @match        https://www.oucbx.com/OUCStudy/LEAP/STUDYWeb/WEB/html/cwExam.html*

// @require      https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.min.js
// @require      https://cdn.bootcdn.net/ajax/libs/js-cookie/latest/js.cookie.min.js

// @grant        none
// ==/UserScript==

(function() {
    'use strict';
    console.log("自动刷课脚本已启动");
    // 题库数据,如果有没有的可自行添加
    // 题库格式: 数组,
    // 单条数据格式: json,
    // 格式内容:
    // {
    // 		"question":"问题", // 问题标题
    // 		"answer":[0] // 答案,格式为数组,值为数字,选择题选项对应 A = 0, B = 1, C = 2, D = 3
    // }
    var tks = [
        { "question": "在通货膨胀条件下，获取收益最大的是（ ）。", "answer": [1] },
        { "question": "商业信用是以商品形态提供的信用，提供信用的一方通常是（ ）。", "answer": [1] },
        { "question": "政府对宏观经济调控难度大所对应的汇率制度是（ ）。", "answer": [0] },
        { "question": "在利率决定理论中，强调投资与储蓄对利率决定作用的是（ ）。", "answer": [2] },
        { "question": "一张还有半年到期的票据面额为4000元，到银行贴现得到3600元。则年贴现率为（ ）。", "answer": [1] },
        { "question": "以下选项中，不属于国际信用的是（ ）。", "answer": [3] },
        { "question": "利率与资金需求之间是（ ）的相关关系，高利率会（ ）资金需求。", "answer": [0] },
        { "question": "牙买加体系下的汇率制度具有多样性，可以归结为三类。（ ）不属于三类中的内容。", "answer": [3] },
        { "question": "金融市场功能的发挥需要些外部条件，不包括（ ）。", "answer": [2] },
        { "question": "结合我国经济金融发展和加入世界贸易组织后开放金融市场的需要，中国人民银行按照（ ）战略步骤推进利率市场化。", "answer": [3] },
        { "question": "目前人民币汇率实行的是（ ）。", "answer": [1] },
        { "question": "（ ）是指金融工具的变现能力或交易对冲能力。", "answer": [1] },
        { "question": "保险市场除了具备一般金融市场的功能外，还有一些特殊的功能是（ ）。", "answer": [0, 1, 2, 4] },
        { "question": "广义金融市场包括（ ）。", "answer": [0, 1, 2, 3] },
        { "question": "经过银行承兑之后的票据信用风险相对较小，是一种信用等级较高的票据。（ ）", "answer": [0] },
        { "question": "在发达的金融市场上，场内交易在交易规模和品种上占有主导地位。（ ）", "answer": [1] },
        { "question": "相对购买力平价是指在某一时点上两国货币之间的兑换比例取决于两国物价总水平变动率。（ ）", "answer": [1] },
        { "question": "下列行为中，应缴纳契税的有（ ）。", "answer": [3] },
        { "question": "下列行为中，既缴纳增值税又缴纳消费税的是（ ）。", "answer": [0] },
        { "question": "以下关于房产税纳税人和征税范围的说法，正确的是（ ）。", "answer": [2] },
        { "question": "税法的调整对象是（ ）。", "answer": [3] },
        { "question": "下列项目适用17%税率征税的有（ ）。", "answer": [2] },
        { "question": "纳税人必须在缴纳有争议的税款后，税务行政复议机关才能受理纳税人的复议申请，这体现了税法适用原则中的（ ）。", "answer": [0] },
        { "question": "税收法律关系产生是指在税收法律关系主体之间形成权利义务关系，其产生的标志是（ ）。", "answer": [3] },
        { "question": "下列关于税务规章的表述中，正确的是（ ）。", "answer": [1] },
        { "question": "下列关于城市维护建设税的表述中，正确的有（ ）。", "answer": [1, 2, 3] },
        { "question": "以下适用“营改增”相关免税政策的有（ ）。", "answer": [0, 1, 2, 3] },
        { "question": "下列税种中，属于间接税的有（ ）。", "answer": [0, 1, 3] },
        { "question": "下列属于税收法律关系主体的有（ ）。", "answer": [0, 1, 2, 3] },
        { "question": "纳税人自产自用的应税消费品，除用于连续生产应税消费品外，凡用于其他方面的，于移送使用时纳税。（ ）", "answer": [0] },
        { "question": "全面深化改革与（ ），被视为“鸟之两翼”“车之双轮”，推动我们的事业滚滚向前。", "answer": [0] },
        { "question": "面对经济新常态，要充分发挥市场在资源配置中的（ ）作用。", "answer": [2] },
        { "question": "1940年1月，在（ ）一文中，毛泽东同志深刻论述了中国革命的历史特点，论述了革命的对象、动力、性质和前途，提出了新民主主义的纲领和道路、第一次旗帜鲜明地提出了新民主主义的完整理论。", "answer": [2] },
        { "question": "党的（ ）明确提出建立社会主义市场经济体制，市场经济成为中国经济发展的总引擎。", "answer": [2] },
        { "question": "习近平总书记提出以“共商、共建、（ ）”为原则的新的全球治理观。", "answer": [2] },
        { "question": "从新时代、新征程、新方位中把握和推进对台工作与和平统一进程，从共创中华民族伟大复兴的美好未来角度实现祖国完全统一。具体而言，就是（ ）、刚柔相济、双管齐下。", "answer": [1] },
        { "question": "（ ）是人民军队始终保持强大的凝聚力、向心力、创造力、战斗力的根本保证。", "answer": [1] },
        { "question": "新发展理念包括创新、（ ）、绿色、开放、共享。", "answer": [3] },
        { "question": "中国共产党的领导是中国特色社会主义最主要的特征。（ ）", "answer": [1] },
        { "question": "全面从严治党，是中国共产党向人民立下的军令状。十八大以来，中共中央相继颁布实施了《中国共产党问责条例》，《中国共产党廉洁自律准则》，《中国共产党纪律处分条例》，修订了《中国共产党党内监督条例》。（ ）", "answer": [0] },
        { "question": "全面依法治国，基本目标是建设中国特色社会主义法治体系，建设社会主义法治国家。（ ）", "answer": [1] },
        { "question": "“一带一路”成为中国崛起进程中新的里程碑，开启了“中国引领、新老大国共治”的全球治理新时代。（ ）", "answer": [0] },
        { "question": "下面关于边际成本和平均成本的说法中哪一个是正确的？（ ）", "answer": [3] },
        { "question": "何种情况下会存在供给大于需求？（ ）", "answer": [2] },
        { "question": "稀缺性的存在意味着：（ ）", "answer": [3] },
        { "question": "在垄断竞争市场上：（ ）", "answer": [1] },
        { "question": "现代市场经济中最重要的企业形式是：（ ）", "answer": [3] },
        { "question": "如果一个企业降低其商品价格后发现总收益增加，这意味着该种商品的：（ ）", "answer": [2] },
        { "question": "光盘价格上升的替代效应是：（ ）", "answer": [0] },
        { "question": "在垄断市场上，价格：（ ）", "answer": [3] },
        { "question": "短期平均成本曲线呈U型，是因为（ ）", "answer": [1] },
        { "question": "现代寡头理论运用古诺模型和拐折的需求曲线来研究寡头的行为。（ ）", "answer": [1] },
        { "question": "生产可能性曲线凹向原点说明了随着一种物品生产的增加，机会成本在递增。（ ）", "answer": [1] },
        { "question": "垄断市场上，无论在短期还是在长期，企业均必将获得超额利润。（ ）", "answer": [1] },
        { "question": "（ ）是专门向经济不发达会员国的私有企业提供贷款和投资的国际性金融组织，于1956年成立，总部设立在华盛顿", "answer": [1] },
        { "question": "（ ）不属于世界银行集团的机构。", "answer": [2] },
        { "question": "生产过程从生产要素的组合到产品销售的全球化称之为（ ）。", "answer": [1] },
        { "question": "经济金融化水平的差异，体现了经济发展水平的差异，其早期表现形式是（ ）", "answer": [0] },
        { "question": "我国出现的第一家现代商业银行是（ ）。", "answer": [1] },
        { "question": "（ ）是金融机构的传统功能，其对商品交易的顺利实现和社会交易成本的节约具有重要意义。", "answer": [2] },
        { "question": "中央银行若提高存款准备金，将（ ）。", "answer": [1] },
        { "question": "下列可能影响货币政策外部时滞的因素是（ ）。", "answer": [1] },
        { "question": "按照戈德史密斯的划分方法，现有金融资产总值在国民财富中所占份额是指（ ）。", "answer": [1] },
        { "question": "人们手中持有的现金代表持有人对中央银行的（ ）。", "answer": [2] },
        { "question": "传统意义上的商业银行以（ ）为主要中间业务。", "answer": [2] },
        { "question": "在不兑现的信用货币制度下，（ ）始终是稳定币值的重要手段，既是用于国际支付的重要储备，也是中央银行重要的资产业务", "answer": [2] },
        { "question": "商业银行的外部组织形式因各国政治经济制度不同而有所不同，目前主要有（ ）等类型。", "answer": [0, 2, 3, 4] },
        { "question": "商业银行投资的证券主要包括政府债券和公司债券，其选择的标准是（ ）。", "answer": [0, 2, 4] },
        { "question": "充分就业目标就是要消除失业，或将失业率降到极低的水平。（ ）", "answer": [1] },
        { "question": "全面推进依法治国，总目标是（ ），建设社会主义法治国家。", "answer": [1] },
        { "question": "被誉为“中国航天之父”和“中国导弹之父”的人是（ ）。", "answer": [2] },
        { "question": "2017年10月18日，中国共产党第十九次全国代表大会在人民大会堂开幕，大会作出的一个重大政治判断是“中国特色社会主义进入了新时代，这是我国发展新的历史方位”。下列关于“新时代”的论述不正确的是（ ）。", "answer": [1] },
        { "question": "我国现行宪法是（ ）。", "answer": [3] },
        { "question": "（ ）提出，倡导富强、民主、文明、和谐，倡导自由、平等、公正、法治，倡导爱国、敬业、诚信、友善，积极培育和践行社会主义核心价值观。", "answer": [1] },
        { "question": "习近平指出，中国人民具有伟大的创造精神、团结精神、梦想精神和（ ）。", "answer": [1] },
        { "question": "“世界是你们的，也是我们的，但是归根结底是你们的。你们青年人朝气蓬勃，正在兴旺的时期，好像早晨八九点钟的太阳，希望寄托在你们身上。”此句话出自（ ）领导人的讲话。", "answer": [1] },
        { "question": "实体法律部门包括（ ）。", "answer": [0, 1, 3] },
        { "question": "在文字处理软件中，WPS和Word等属于", "answer": [2] },
        { "question": "键盘可以作为微机输入设备", "answer": [0] },
        { "question": "世界上第一台通用计算机“ENIAC”于1953年在美国宾夕法尼亚大学诞生。", "answer": [1] },
        { "question": "CPU由运算器和控制器组成", "answer": [0] },
        { "question": "Cache设置在CPU和主存储器之间，提高了计算机系统的运行效率。", "answer": [0] },
        { "question": "WPS和Word等文字处理软件属于系统软件。", "answer": [1] },
        { "question": "ALU的中文名称是运算器。", "answer": [0] },
        { "question": "MS Office是操作系统。", "answer": [1] },
        { "question": "地址总线上既可传送地址信息，也可传送控制信息和其他信息。", "answer": [1] },
        { "question": "在计算机硬件技术指标中，度量存储器空间大小的基本单位是", "answer": [2] },
        { "question": "计算机按性能可以分为超级计算机、大型计算机、小型计算机、微型计算机和", "answer": [2] },
        { "question": "计算机的存储器中，组成一个字节（Byte）的二进制位（bit）个数是", "answer": [1] },
        { "question": "在Word窗口中，标题栏右端的“—”表示最小化按钮可将Word窗口最小化到任务栏上。", "answer": [0] },
        { "question": "在word中，要想文件可以让他人浏览但不能修改，可以在“工具”菜单下的“选项”中设置修改权限口令。", "answer": [0] },
        { "question": "Word 2010中自定义功能区设定在", "answer": [1] },
        { "question": "等于每行中最大字符高度两倍的行距被称为（   ）行距。", "answer": [0] },
        { "question": "在Word 2010被使用的过程中，能够显示页面四角的视图方式是", "answer": [1] },
        { "question": "在word使用的过程中，段落格式应用于插入点所在的段落。", "answer": [0] },
        { "question": "在Excel工作表中，若已将A1单元格中的内容在A1:E1区域中跨列居中，要在编辑栏修改其内容，必须选定单元格A1。", "answer": [0] },
        { "question": "Office 是一种常用的办公软件，是由（   ）公司开发的软件。", "answer": [1] },
        { "question": "在excel 2010中，输入当前时间可按组合键Shift+；", "answer": [1] },
        { "question": "在Excel“格式”工具栏中提供了（   ）种对齐方式。", "answer": [0] },
        { "question": "要键入（   ）先导符号，才能在一个单元格内输入公式。", "answer": [3] },
        { "question": "Excel被使用的过程中，工作表的自动筛选被取消后，工作表恢复原样。", "answer": [0] },
        { "question": "稀缺性的存在意味着：", "answer": [3] },
        { "question": "光盘价格上升的替代效应是：", "answer": [0] },
        { "question": "何种情况下会存在供给大于需求？", "answer": [2] },
        { "question": "下面关于边际成本和平均成本的说法中哪一个是正确的？", "answer": [3] },
        { "question": "在垄断市场上，价格：", "answer": [2] },
        { "question": "生产可能性曲线凹向原点说明了随着一种物品生产的增加，机会成本在递增。", "answer": [0] },
        { "question": "2003年12月31日的外汇储备量是流量。", "answer": [1] },
        { "question": "一场台风摧毁了某地区的荔枝树，市场上的荔枝少了，这称为供给减少。", "answer": [0] },
        { "question": "企业用自有资金进行生产是有成本的。", "answer": [0] },
        { "question": "短期边际成本曲线与短期平均成本曲线的交点就是停止营业点。", "answer": [1] },
        { "question": "边际收益等于边际成本时，企业的正常利润达到最大化。", "answer": [1] },
        { "question": "最全面反映经济中物价水平变动的物价指数是：", "answer": [2] },
        { "question": "经济增长是指：", "answer": [1] },
        { "question": "引起摩擦性失业的原因：", "answer": [3] },
        { "question": "中央银行规定的银行所保持的最低准备金与存款的比率是", "answer": [3] },
        { "question": "在LM曲线不变的情况下，自发总支出减少会引起：", "answer": [3] },
        { "question": "由于总需求不足而形成的失业属于", "answer": [2] },
        { "question": "居民购买住房属于投资。", "answer": [0] },
        { "question": "技术进步是实现经济增长的必要条件。", "answer": [0] },
        { "question": "货币数量论认为，流通中的货币数量越多，商品价格水平越低，货币价值越大。", "answer": [1] },
        { "question": "企业债券的利率高于政府债券的利率。", "answer": [0] },
        { "question": "在物品市场上，利率与国内生产总值成同方向变动。", "answer": [1] },
        { "question": "无论在长期或短期中都存在的失业就是自然失业。", "answer": [1] },
        { "question": "下列关于税务规章的表述中，正确的是", "answer": [1] },
        { "question": "纳税人必须在缴纳有争议的税款后，税务行政复议机关才能受理纳税人的复议申请，这体现了税法适用原则中的", "answer": [0] },
        { "question": "税收法律关系产生是指在税收法律关系主体之间形成权利义务关系，其产生的标志是", "answer": [3] },
        { "question": "下列项目适用17%税率征税的有", "answer": [2] },
        { "question": "快递收派服务属于", "answer": [3] },
        { "question": "税法的调整对象是", "answer": [3] },
        { "question": "下列行为中，既缴纳增值税又缴纳消费税的是", "answer": [0] },
        { "question": "下列税种中，属于间接税的有", "answer": [1, 3] },
        { "question": "下列属于税收法律关系主体的有", "answer": [1, 2, 3] },
        { "question": "税收规章是由国务院税务主管部门制定的税收部门规章。", "answer": [0] },
        { "question": "对从事生产、委托加工、进口和出口应税消费品的单位和个人，都应当征收消费税。", "answer": [1] },
        { "question": "纳税人自产自用的应税消费品，除用于连续生产应税消费品外，凡用于其他方面的，于移送使用时纳税。", "answer": [0] },
        { "question": "如有随堂练习的，需完成习题并达到（  ）的通过率后才能显示已通过，如果未达到指定的通过率，可重复测试并查看答案解析，直至通过为止。", "answer": [2] },
        { "question": "每学期期末考试前一周学院会组织专门的（  ），主要目的是让学生熟悉网络考试的系统与环境，同时做各课程的期末复习。", "answer": [3] },
        { "question": "（  ）是成人教育学历中的一种。是指使用电视及互联网等传播媒体的教学模式，它突破了时空的界线。", "answer": [2] },
        { "question": "（  ）是指通过网络表现的某门学科的教学内容及实施的教学活动的总和。", "answer": [0] },
        { "question": "（  ）是指通过网络表现的某门学科的教学内容及实施的教学活动的总和。", "answer": [0] },
        { "question": "国家开放大学学务处理功能区功能不包括：", "answer": [3] },
        { "question": "关于IE浏览器的功能，下面说法正确的是（  ）。", "answer": [0] },
        { "question": "国家开放大学学生事务与教师发展中心负责规划和管理（  ）。", "answer": [1] },
        { "question": "远程接待中心的工作理念是：（  ）。", "answer": [0] },
        { "question": "保宝APP初次登录账号是身份证号码，密码是（   ）。", "answer": [2] },
        { "question": "课程作业也是必修内容，需完成后分数达到（   ）分以上才算测试通过。", "answer": [3] },
        { "question": "在保宝网找回密码时，如果进行【忘记密码】操作之后，依旧不能找回密码登录账号，可以选择进行（ ）。", "answer": [3] },
        { "question": "党的十九大作出了从决胜全面建设小康社会到基本实现现代化，再到全面建成社会主义现代化国家的新的战略安排。", "answer": [1] },
        { "question": "1940年1月，在（   ）一文中，毛泽东同志深刻论述了中国革命的历史特点，论述了革命的对象、动力、性质和前途，提出了新民主主义的纲领和道路、第一次旗帜鲜明地提出了新民主主义的完整理论。", "answer": [2] },
        { "question": "中国共产党的领导是中国特色社会主义最主要的特征。", "answer": [1] },
        { "question": "去库存，主要是通过对房地产市场的积极调控，减轻一线热门城市房价上涨压力，消化三四线城市楼市积压。", "answer": [0] },
        { "question": "面对经济新常态，要充分发挥市场在资源配置中的（   ）作用。", "answer": [2] },
        { "question": "全面依法治国，基本目标是建设中国特色社会主义法治体系，建设社会主义法治国家。", "answer": [1] },
        { "question": "新发展理念包括创新、（   ）、绿色、开放、共享。", "answer": [3] },
        { "question": "“一国两制”在香港、澳门的实践取得了举世公认的成功，改变了历史上但凡收复失地都要大动干戈的历史定律，这是中国的一个伟大创举，为国际社会解决类似问题提供了新思路新方案。", "answer": [0] },
        { "question": "从新时代、新征程、新方位中把握和推进对台工作与和平统一进程，从共创中华民族伟大复兴的美好未来角度实现祖国完全统一。具体而言，就是（   ）、刚柔相济、双管齐下。", "answer": [1] },
        { "question": "（   ）是人民军队始终保持强大的凝聚力、向心力、创造力、战斗力的根本保证。", "answer": [1] },
        { "question": "“一带一路”成为中国崛起进程中新的里程碑，开启了“中国引领、新老大国共治”的全球治理新时代。", "answer": [1] },
        { "question": "习近平总书记提出以“共商、共建、（   ）”为原则的新的全球治理观。", "answer": [2] },
        { "question": "2017年10月18日，中国共产党第十九次全国代表大会在人民大会堂开幕，大会作出的一个重大政治判断是“中国特色社会主义进入了新时代，这是我国发展新的历史方位”。下列关于“新时代”的论述不正确的是", "answer": [1] },
        { "question": "“世界是你们的，也是我们的，但是归根结底是你们的。你们青年人朝气蓬勃，正在兴旺的时期，好像早晨八九点钟的太阳，希望寄托在你们身上。”此句话出自（ ）领导人的讲话。", "answer": [1] },
        { "question": "信仰是人们在实践中形成的、具有现实可能性的对未来的向往和追求。（ ）", "answer": [0] },
        { "question": "习近平指出，中国人民具有伟大的创造精神、团结精神、梦想精神和（ ）。", "answer": [1] },
        { "question": "爱国主义是一个民族、一个国家在长期发展过程中逐渐形成的、赖以生存和发展的一种基本精神，因此是一成不变的。（ ）", "answer": [1] },
        { "question": "（）提出，倡导富强、民主、文明、和谐，倡导自由、平等、公正、法治，倡导爱国、敬业、诚信、友善，积极培育和践行社会主义核心价值观。", "answer": [1] },
        { "question": "中国特色社会主义建设是社会主义核心价值观的理论来源。（）", "answer": [1] },
        { "question": "《中共中央关于加强社会主义精神文明建设若干问题的决议》规定了我们今天各行各业都应共同遵守的职业道德的五项基本规范，即“爱岗敬业、诚实守信、办事公道、服务群众、奉献社会”。其中，（ ）是社会主义职业道德的核心规范，它是贯穿于全社会共同的职业道德之中的基本精神。", "answer": [0] },
        { "question": "“为人民服务”最早出自毛主席纪念张思德同志的讲话。（ ）", "answer": [0] },
        { "question": "全面推进依法治国，总目标是（ ），建设社会主义法治国家。", "answer": [1] },
        { "question": "共产党领导、多党派合作，共产党执政、多党派参政是中国政党制度的基本特色，也是我国政治制度的一大优势。（ ）", "answer": [0] },
        { "question": "我国现行宪法是（ ）。", "answer": [3] },
        { "question": "党的十九大提出了“培养担当民族复兴大任的时代新人”的战略要求。大学生应该以（ ）为根本要求，成为中国特色社会主义事业的合格建设者和可靠接班人，成为走在时代前列的奋进者、开拓者、奉献者。", "answer": [3] },
        { "question": "下列关于思想道德与法律的辩证统一关系说法最恰当的是（ ）。", "answer": [0] },
        { "question": "坚持和发展中国特色社会主义，既要发挥思想道德的引领和教化作用，又要发挥法律的规范和强制作用。（ ）", "answer": [0] },
        { "question": "在习近平新时代中国特色社会主义阶段，我们每个人选择和确立人生目的，应该（ ）。", "answer": [3] },
        { "question": "影片《袁隆平口述自传》有这样一段：“如果读小学的时候老师带我们郊游去的不是那个园艺场，而是真正的农村，是这样又苦又脏又累又穷的地方，恐怕我就不会立志学农了。”“既然选择学农了，我也没觉得后悔，而是坚定了学农的信心。”“看到农民这么苦，我就暗下决心，立志要改造农村，为农民做点实事。”“小时候亲眼目睹了中国饱受日寇的欺凌，我深深感到中国应该强大起来。特别是新中国诞生后，觉得中国人民真的是站起来了，我也要做一番事业，为中国人争一口气，为自己的国家做贡献，这是最大的心愿。所以，我感到自己肩上应该有担子。”结合影片中袁隆平先生的自述，关于怎样实现人生价值，创造有意义的人生，以下不符合实际的一项是（ ）。", "answer": [0] },
        { "question": "《世说新语·德行》记录的《管宁割席》故事中，当士大夫乘坐的华贵车辆从门前经过时，管宁和华歆都像原来一样坐在席子上读书。（ ）", "answer": [1] },
        { "question": "人生目的是指人为什么活着这一人生根本问题的认识和回答。（ ）", "answer": [0] },
        { "question": "理想作为人类的一种精神现象，是人类进入文明社会的产物。（ ）", "answer": [1] },
        { "question": "习近平总书记指出，人无（ ）则不立，国无精神则不强。", "answer": [2] },
        { "question": "《论语·雍也》载：“贤哉，回也！一箪食，一瓢饮，在陋巷。人不堪其忧，回也不改其乐。贤哉，回也！”告诉我们快乐在于精神追求。（ ）", "answer": [0] },
        { "question": "做好新时代青年工作，就必须引导广大青年将（）作为思想武器和行动指南，深刻认识其历史地位、丰富内涵、精神实质和实践要求，深入领会新时代坚持和发展中国特色社会主义十四条基本方略，切实增强“四个意识”，牢固树立“四个自信”，勇做担当民族复兴大任的时代新人，最大限度地实现人生价值。", "answer": [1] },
        { "question": "坚定社会主义核心价值观自信，要求我们自觉以科学文化知识来引领多样化的社会思潮，增强抵御错误价值观侵蚀的能力。", "answer": [1] },
        { "question": "习近平强调：“道德建设，重要的是激发人们形成善良的道德意愿、道德情感，培育正确的道德判断和道德责任，提高道德实践能力尤其是自觉践行能力。”大学生锤炼高尚道德品格，就要做到（ ）。", "answer": [2] },
        { "question": "道德决定一切、高于一切、支配一切，只要道德水平高，一切社会问题都可以迎刃而解。（ ）", "answer": [1] },
        { "question": "制定和认可是法律创制的主要方式。制定是指国家机关通过立法活动产生新规范。认可是国家对既存的行为规则予以承认，赋予法律效力。（ ）", "answer": [0] },
        { "question": "基层群众性自治组织包括城市和农村按居民居住地区设立的居民委员会、村民委员会。（ ）", "answer": [0] },
        { "question": "新中国的第一部宪法是（ ）。", "answer": [1] },
        { "question": "习近平总书记说：“青年一代有理想、有担当，国家就有前途，（）”。", "answer": [0] },
        { "question": "人生观的主要内容包括（ ）。", "answer": [0, 1, 2] },
        { "question": "人生态度是指人们通过生活实践形成的对人生问题的一种稳定的（ ）。", "answer": [0, 2] },
        { "question": "五四精神，核心内容是爱国、进步、民主、自由。（ ）", "answer": [1] },
        { "question": "在革命、建设、改革各个历史时期，中国共产党坚持马克思主义基本原理同中国具体实际相结合，运用马克思主义立场、观点、方法研究解决各种重大理论和实践问题，不断推进马克思主义（ ），指导党和人民取得了新民主主义革命、社会主义革命和社会主义建设、改革开放的伟大成就。", "answer": [1, 2, 3] },
        { "question": "被誉为“中国航天之父”和“中国导弹之父”的人是（ ）。", "answer": [2] },
        { "question": "在当代中国，社会发展离不开改革创新，改革创新是社会发展的重要动力，坚持改革创新是新时代的迫切要求。以下理解错误的是（ ）。", "answer": [3] },
        { "question": "在文字处理软件中，WPS和Word等属于", "answer": [2] },
        { "question": "民族精神是一个民族在长期共同生活和社会实践中形成的，为本民族大多数成员所认同的（ ）的总和，是一个民族赖以生存和发展的精神支柱。", "answer": [0, 1, 2, 3] },
        { "question": "勤劳勇敢的中国人民培育、继承、发展起来的以爱国主义为核心的伟大民族精神，是坚定中国特色社会主义（ ）的底气，是中华民族风雨无阻、高歌行进的根本力量。", "answer": [0, 1, 2, 3] },
        { "question": "高举中国特色社会主义伟大旗帜，以（ ）为指导，深入学习贯彻党的十九大精神", "answer": [0, 1, 2, 3] },
        { "question": "培育和践行社会主义核心价值观要从小抓起、从学校抓起。坚持（ ），围绕立德树人的根本任务，把社会主义核心价值观纳入国民教育总体规划", "answer": [0, 2] },
        { "question": "为什么说孔子身上具有优秀的传统美德？（ ）", "answer": [2] },
        { "question": "大自然是人类的（ ）。", "answer": [0] },
        { "question": "红军长征中为寻找出一些无毒、可以食用的野菜、野草，渡过饥饿的难关需要冒着中毒的危险尝出一种能吃的野草、野菜。张思德在“尝百草”活动中，总是让身边的工作人员先尝。", "answer": [1] },
        { "question": "站在新的历史起点上，革命前辈们在艰苦卓绝的革命斗争中培育起来的革命精神和优良传统，永远是我们在前进道路上战胜各种困难和风险、不断夺取新胜利的强大精神力量。（ ）", "answer": [0] },
        { "question": "我国现行宪法在序言中明确规定：“本宪法以法律的形式确认了中国各族人民奋斗的成果，规定了国家的根本制度和根本任务，是国家的根本法，具有最高的法律效力。” （ ）", "answer": [0] },
        { "question": "一些法律、法规，在某些特殊情况下，可以同宪法相抵触。（ ）", "answer": [1] },
        { "question": "法的基本特征包括（ ）。", "answer": [0, 1, 2, 3] },
        { "question": "中国特色社会主义法律体系已经形成并不断发展，这一法律体系是以宪法为统帅，还包括在宪法统帅下形成的（ ）。", "answer": [1, 3] },
        { "question": "实体法律部门包括（ ）。", "answer": [0, 1, 3] }
    ]
    var sets = setInterval(function() {
        if (typeof Cookies != 'undefined' && typeof $ != 'undefined') {
            console.log("依赖项加载成功");
            clearInterval(sets)
            var links = window.location.href
            if (links.includes("mystudy.html")) {} else if (links.includes("courseDetails.html")) {
                //课程详情
                akk()
            } else if (links.includes("cwVideoPlay.html")) {
                //课程学习
                ssp()
            } else if (links.includes("cwPDF.html") || links.includes("viewer.html")) {
                //pdf阅读
                pdf()
            } else if (links.includes("cwExercise.html") || links.includes("cwExerciseResult.html") || links.includes("cwExercisePaser.html")) { // 答题页面
                dts()
            }
            if (
                links.includes("cwQa.html") ||
                links.includes("cwExam.html")) {
                next_page()
            }
        } else {
            console.log("加载依赖中");
        }
    }, 500)

    //课程介绍
    function akk() {
        console.log("课程介绍");
        var cok = Cookies.get('kc_num')
        var but = $("[class='button primary']").eq(0).text()
        if (but != "重新学习") {
            $("[class='button primary']").eq(0)[0].click()
        } else {
            console.log("此课程已学习完成");
            Cookies.set('kc', cok)
            $("#mystudy")[0].click();
        }
    }
    //pdf文案
    function pdf() {
        console.log("pad页面");
        setInterval(function() {
            console.log("滚动中");
            $("[class='w h']").contents().find("#viewerContainer").scrollTop(99999999999)
            if ($(".tsuccess").text() !== "") {
                console.log("已学习");
                next_page()
            }
        }, 1000)
    }
    //视频
    function ssp() {
        var myVid = document.getElementById("myPlayer_html5_api");
        // document.querySelector("video").src="https://vod.300hu.com/4c1f7a6atransbjngwcloud1oss/5d82e52a382171174942019585/v.f30.mp4"
        document.querySelector("video").playbackRate = 3.5; // 锁定播放速度在倍
        myVid.volume = 0;
        $(".vjs-big-play-button").click()
        var timesRun = 0;//5分钟刷新下界面
        setInterval(function() {
            console.log("监听中："+timesRun);
            timesRun += 1;
            if(timesRun === 300){
                flush();//定时刷新界面
            }
            if ($(".video-js").hasClass("vjs-paused")) {
                $(".vjs-big-play-button").click()
            }
            if ($(".tsuccess").text() !== "") {
                console.log("已学习");
                next_page()
            }
        }, 1000)
        myVid.addEventListener('pause', function() { //暂停开始执行的函数
            console.log("播放a");
            myVid.play()
        });
    }


    // 答题
    function next_page() {
        console.log("下一课");
        setInterval(function() {
                if ($(".tree-content div").length > 0) {
                for (var i = 0; i < $(".tree-content div").length; i++) {
                    if ($(".tree-content div:eq(" + i + ") [class='status fail']").length !== 0 && ($(".tree-content div:eq(" + i + ") [class='col v-m icon'] img").attr("src") === "../img/video.png" || $(".tree-content div:eq(" + i + ") [class='col v-m icon'] img").attr("src") === "../img/PDF.png")) {
                        $(".tree-content div:eq(" + i + ") a")[0].click();
                        break
                    }
                }
            }
        }, 1000)
    }


    function flush(){
        console.log("定时刷新界面");
        location.reload();
        console.log('每隔1分钟执行一次')
    }




    function kksa(json_answer) {
        var i = 0
        var m = 0
        var times
        console.log("答题模块")

        // if (!localStorage.getItem("question_list")) {
        // console.log("获取答案")
        // Cookies.set('dn', 1)
        // commitExercise()
        // } else {
        setInterval(function() {
            if ($(".exam-num").eq(i).hasClass("finish")) {
                console.log("下一题")
                clearInterval(times)
                $("[class='button primary outline w']:eq(1)")[0].click();
                i++
                setTimeout(function() {
                    document.querySelectorAll("input").forEach((el, index) => {
                        el.checked = false
                    })
                }, 500)
                setTimeout(function() {
                    cels()
                }, 1000)
            }
        }, 1500)
        // }

        function cels() {
            if ($(".exam-card .grid-item").length > i) {

                let noAnser = true
                for (let j = 0; j < json_answer.length; j++) {
                    if ($(".exam dt").text().substr($(".exam dt").text().indexOf("、") + 1).includes(json_answer[j].question)) {
                        noAnser = false;
                        tempFun(j)

                        function tempFun(l) {
                            // setTimeout(function() {
                            console.log("找到答案", i, json_answer[l]);
                            if (json_answer[l].answer) {
                                for (var k = 0; k < json_answer[l].answer.length; k++) {
                                    $(".exam .item:eq(" + json_answer[l].answer[k] + ") input").click()
                                }
                            } else {
                                document.querySelectorAll(".exam .item").forEach((el, index) => {
                                    // console.log("学习内容", json_answer[l].qsd[0], el.innerText.substring(2), json_answer[l].qsd.indexOf(el.innerText.substring(2)))
                                    if (json_answer[l].qsd.indexOf(el.innerText.substring(2)) !== -1) {
                                        console.log("答案", json_answer[l].qsd[json_answer[l].qsd.indexOf(el.innerText.substring(2))])
                                        console.log(el)
                                        if (!$(el).find("input")[0].checked) {
                                            $(el).find("input").click()
                                        }
                                    }
                                })
                            }
                            // }, 1000)
                        }
                    }
                }
                if (noAnser) {
                    console.log("没有找到这道题的答案", i);
                    $(".exam .item:eq(0) input").click()
                }
            } else {
                console.log("答题完成");
                Cookies.set('dn', 1)
                commitExercise()
            }
        }
        cels()
        // let mat_dt = setInterval(function() {
        //     if ($(".exam-card .grid-item").length > i) {
        //         // $(".exam .item:eq(0) input").click()
        //         for (var j = 0; j < json_answer.length; j++) {
        //             if ($(".exam dt").text().substr($(".exam dt").text().indexOf("、") + 1).includes(json_answer[j].question)) {
        //                 console.log("找到答案");
        //                 console.log(json_answer[j]);
        //                 $(".exam .item:eq(0) input").click()
        //                 for (var k = 0; k < json_answer[j].answer.length; k++) {
        //                     $(".exam .item:eq(" + json_answer[j].answer[k] + ") input").click()
        //                 }
        //             }
        //         }
        //         if ($(".exam-num").eq(i).hasClass("finish")) {
        //             $("[class='button primary outline w']:eq(1)")[0].click();
        //             i++
        //             m = 0
        //         }
        //         // 网络问题超时强制跳过
        //         if (m >= 10) {
        //             $("[class='button primary outline w']:eq(1)")[0].click();
        //             i++
        //             m = 0
        //         }
        //         m++
        //     } else {
        //         console.log("答题完成");
        //         Cookies.set('dn', 1)
        //         commitExercise()
        //         clearInterval(mat_dt)
        //     }
        // }, 1000)
    }

    function dts() {
        var da = Cookies.get('dn')
        if (da == undefined) {
            console.log("一阶段,随机填写答案");
            localStorage.clear();
            let check = setInterval(function() {
                if ($(".exam-card .grid-item").length !== 0) {
                    // 防止卡机
                    var nookkd = true
                    $("[class='button primary outline w']").each(function(index, el) {
                        nookkd = !nookkd
                        if ($(el).text() === "再做一次") $(el)[0].click();
                    })
                    if (nookkd) {
                        kksa(tks)
                    }
                    clearInterval(check)
                }
            }, 100)
            } else if (da === "1") {
                console.log("二阶段,查看正确答案/查看是否通过");
                let await_ajax = setInterval(function() {
                    $("h2").each((index, el) => {
                        // 如果已经通过
                        if ($(el).text().includes("测试通过")) {
                            console.log("通过测试");
                            Cookies.remove('dn')
                            Cookies.remove('question_list')
                            Cookies.remove('isokk')
                            next_page()
                            clearInterval(await_ajax)
                        } else if ($(el).text().includes("您未能通过测试")) {
                            console.log("没有通过");
                            var on_ok = true
                            $("[class='button primary outline w']").each(function(index, el) {
                                Cookies.set('dn', 2)
                                if ($(el).text() === "查看解析") {
                                    on_ok = false
                                    $(el)[0].click()
                                };
                                clearInterval(await_ajax)
                            })
                            if (on_ok) {
                                Cookies.remove('dn')
                                Cookies.remove('question_list')
                                Cookies.remove('isokk')
                                next_page()
                                clearInterval(await_ajax)
                            }
                        }
                    })
                }, 100)
                } else if (da === "2") {
                    console.log("三阶段,将答案写入cookie");
                    let await_ajax = setInterval(function() {
                        if ($(".exam-card .grid-item").length !== 0) {
                            kksas()
                            clearInterval(await_ajax)
                        }
                    }, 100)

                    function kksas() {
                        var i = 0;
                        var temp_arr = JSON.parse(localStorage.getItem("question_list")) || []
                        console.log(temp_arr)
                        let await_get_qus = setInterval(function() {
                            if (i < $(".exam-card .grid-item").length) {
                                var ans = $(".jiexi .content p:last-child").text().substring(5).split("")
                                var ten = []
                                for (var s = 0; s < ans.length; s++) {
                                    ans[s] = ans[s].charCodeAt() - 65
                                }
                                for (var p = 0; p < ans.length; p++) {
                                    ten.push(document.querySelectorAll(".exam .item")[ans[p]].innerText.substring(2))
                                }
                                temp_arr.push({ "question": $(".exam dt").text().substr(3), "qsd": ten })
                                $("[class='button primary outline w']").eq(1)[0].click();
                                i++
                            } else {
                                localStorage.setItem("question_list", JSON.stringify(temp_arr));
                                console.log(localStorage.getItem("question_list"))
                                Cookies.set('question_list', temp_arr)
                                Cookies.set('dn', 3)
                                clearInterval(await_get_qus)
                                console.log(temp_arr)
                                $("[class='row w current'] [class='col v-m oneline']").click()
                            }
                        }, 300)
                        }
                } else if (da === "3") {
                    console.log("回填");
                    Cookies.set('dn', 4)
                    setInterval(function() {
                        $("[class='button primary outline w']").each(function(index, el) {
                            if ($(el).text() === "再做一次") $(el)[0].click();
                        });
                    }, 300)
                } else if (da === "4") {
                    // var dnlb = JSON.parse(Cookies.get('question_list'))
                    var dnlb = JSON.parse(localStorage.getItem("question_list"));
                    console.log(dnlb);
                    var i = 0
                    let check = setInterval(function() {
                        if ($(".exam-card .grid-item").length !== 0) {
                            Cookies.set('isokk', "ok")
                            // 防止卡机
                            var nookkd = true
                            $("[class='button primary outline w']").each(function(index, el) {
                                nookkd = !nookkd
                                if ($(el).text() === "再做一次") $(el)[0].click();
                            })
                            if (nookkd) {
                                kksa(dnlb)
                            }
                            clearInterval(check)
                        }
                    }, 100)
                    }
    }
})();