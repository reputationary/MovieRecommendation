# import random
#
# # 生成200个随机用户名和密码
# users = [(f'user{i}', f'password{i}') for i in range(1, 201)]
#
# # 构建插入语句
# insert_statements = []
# for user in users:
#     insert_statements.append(f"INSERT INTO User (username, password) VALUES ('{user[0]}', '{user[1]}');")
#
# # 输出插入语句
# for statement in insert_statements:
#     print(statement)

# import string
# import random
#
# # 生成200个随机用户名和密码
# users = []
# for i in range(1, 201):
#     # 生成随机用户名
#     username = ''.join(random.choices(string.ascii_letters + string.digits, k=5))
#     # 生成随机密码
#     password = ''.join(random.choices(string.ascii_letters + string.digits, k=11))
#     users.append((username, password))
#
# # 构建插入语句
# insert_statements = []
# for user in users:
#     insert_statements.append(f"INSERT INTO User (username, password) VALUES ('{user[0]}', '{user[1]}');")
#
# # 输出插入语句
# for statement in insert_statements:
#     print(statement)


# import random
# import string
#
# # 定义常用中文字符集
# CHINESE_CHARS = "一二三四五六七八九十百千万人口天地水火风云山川田土金木草花果"
#
# # 生成200个随机用户名和密码
# users = []
# used_usernames = set()  # 用于去重的set
# for i in range(1, 201):
#     # 生成随机中文用户名
#     while True:
#         username = ''.join(random.sample(CHINESE_CHARS, random.randint(2, 3)))
#         if username not in used_usernames:
#             used_usernames.add(username)
#             break
#     # 生成随机中英文混合密码
#     password = ''.join(random.choices(string.ascii_letters + string.digits, k=random.randint(10, 12)))
#     users.append((username, password))
#
# # 构建插入语句
# insert_statements = []
# for user in users:
#     insert_statements.append(f"INSERT INTO User (username, password) VALUES ('{user[0]}', '{user[1]}');")
#
# # 输出插入语句
# for statement in insert_statements:
#     print(statement)

import random
import string

# 生成200个随机用户名和密码
users = []
used_usernames = set()  # 用于去重的set
used_passwords = set()  # 用于去重的set
while len(users) < 200:
    # 生成随机用户名
    username = ''.join(random.choices(string.ascii_letters + string.digits, k=5))
    # 生成随机密码
    password = ''.join(random.choices(string.ascii_letters + string.digits, k=11))
    if username not in used_usernames and password not in used_passwords:
        used_usernames.add(username)
        used_passwords.add(password)
        users.append((username, password))

# 构建插入语句
insert_statements = []
for user in users:
    insert_statements.append(f"INSERT INTO User (username, password) VALUES ('{user[0]}', '{user[1]}');")

# 输出插入语句
for statement in insert_statements:
    print(statement)
