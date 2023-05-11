# 导入模块
import requests
from lxml import etree
import pymysql


# 请求头信息
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.131 Safari/537.36 SLBrowser/8.0.1.3162 SLBChan/10'
}


# 功能函数，找到列表第一个元素，不存在返回空
def get_first_text(list):
    try:
        return list[0].strip()
    except:
        return ""


# 使用列表生成式表示10个页面的地址
urls = ['https://movie.douban.com/top250?start={}&filter='.format(str(i * 25)) for i in range(10)]
count = 1  # 用来计数
for url in urls:
    # print(url)
    res = requests.get(url=url, headers=headers)  # 发起请求
    # print(res.status_code)
    html = etree.HTML(res.text)  # 将返回的文本加工成可以解析的html
    lis = html.xpath('//*[@id="content"]/div/div[1]/ol/li')  # 获取每个电影的li元素
    # print(len(lis))
    # 解析数据
    for li in lis:
        img_url = get_first_text(li.xpath('./div/div[1]/a/img/@src'))  # 获取电影封面
        title = get_first_text(li.xpath('./div/div[2]/div[1]/a/span[1]/text()'))  # 电影标题
        src = get_first_text(li.xpath('./div/div[2]/div[1]/a/@href'))  # 电影链接
        dictor = get_first_text(li.xpath('./div/div[2]/div[2]/p[1]/text()'))  # 导演
        score = get_first_text(li.xpath('./div/div[2]/div[2]/div/span[2]/text()'))  # 评分
        comment = get_first_text(li.xpath('./div/div[2]/div[2]/div/span[4]/text()'))  # 评价人数
        parts1 = comment.split('人')
        commentPeople, ren = parts1;
        summery = get_first_text(li.xpath('./div/div[2]/div[2]/p[2]/span/text()'))  # 电影简介
        yearCountryType = get_first_text(li.xpath('./div/div[2]/div[2]/p[1]/text()[2]')) # 电影年份，国家，类型
        parts2 = yearCountryType.split('/')
        if len(parts2) == 3:
            year, country, type = parts2
        else:
            print('貌似格式不对！！！！')

        # 下载图片到本地
        # response = requests.get(img_url, stream=True)
        # with open("img/{}.jpg".format(count), "wb") as f:
        #     for chunk in response.iter_content(chunk_size=1024):
        #         f.write(chunk)

        # 打印电影信息
        print("%s 名称:《%s》\t\t 链接: %s\t\t %s\t\t 评分: %s\t 评论人数: %s\t 简介：%s\t\t 图片链接：%s\t\t 年份:%s\t\t 国家:%s\t\t 类型：%s" % (
            str(count), title, str(src), dictor, str(score), commentPeople, summery, img_url, year, country, type))
        count += 1
        # 构造 SQL 语句
        # 构造 SQL 语句
        # sql = "INSERT INTO movies(title, src, director, score, comment, summary, img_url) VALUES ('{}', '{}', '{}', {}, {}, '{}', '{}')".format(
        #     title.replace("'", "''"), src.replace("'", "''"), dictor.replace("'", "''"), score, comment,
        #     summery.replace("'", "''"), img_url.replace("'", "''"))
        # print(sql)
        # sql = "INSERT INTO movies(title, src, director, score, commentPeople, summary, img_url, year, country, type) VALUES ('{}', '{}', '{}', {}, {}, '{}', '{}', {}, '{}', '{}');".format(
        #     title.replace("'", "''"), src.replace("'", "''"), dictor.replace("'", "''"), score, commentPeople,
        #     summery.replace("'", "''"), img_url.replace("'", "''"), year, country, type)
        # print(sql)

# # 建立连接
# conn = pymysql.connect(
#     host="localhost",
#     user="root",
#     password="1234",
#     database="movies"
# )
#
# # 获取游标
# cursor = conn.cursor()
#
# # 执行 SQL 语句
# sql = "INSERT INTO movies(title, src, director, score, comment, summary, img_url) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')"
# cursor.execute(sql % (title, src, dictor, score, comment, summery, img_url))
#
# # 提交事务
# conn.commit()
#
# # 关闭游标和连接
# cursor.close()
# conn.close()
