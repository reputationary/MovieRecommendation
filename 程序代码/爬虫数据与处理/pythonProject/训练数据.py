# import random
#
# # 生成20000条数据并输出到文件
# with open(r'C:\Users\lenovo\Desktop\训练数据.txt', 'w') as f:
#     for i in range(20000):
#         user_id = random.randint(1, 200)
#         movie_id = random.randint(1, 250)
#         rating = random.randint(3, 5)
#         data_line = f"{user_id} {movie_id} {rating}\n"
#         sql_line = f"INSERT INTO movie_ratings (user_id, movie_id, rating) VALUES ({user_id}, {movie_id}, {rating});\n"
#         f.write(data_line)
#         f.write(sql_line)


# import random
#
# # 生成20000条数据并输出到文件
# with open(r'C:\Users\lenovo\Desktop\训练数据.txt', 'w') as f:
#     # 初始化已经生成的ID组合集合
#     id_pair_set = set()
#
#     for i in range(20000):
#         # 生成不重复的ID组合
#         while True:
#             user_id = random.randint(1, 200)
#             movie_id = random.randint(1, 250)
#             id_pair = (user_id, movie_id)
#             if id_pair not in id_pair_set:
#                 id_pair_set.add(id_pair)
#                 break
#
#         rating = random.randint(3, 5)
#         data_line = f"{user_id} {movie_id} {rating}\n"
#         sql_line = f"INSERT INTO movie_ratings (user_id, movie_id, rating) VALUES ({user_id}, {movie_id}, {rating});\n"
#         f.write(data_line)
#         f.write(sql_line)



import random

# 生成20000条数据并输出到文件
data_lines = []
sql_lines = []
id_pair_set = set()

while len(id_pair_set) < 20000:
    # 生成不重复的ID组合
    while True:
        user_id = random.randint(1, 200)
        movie_id = random.randint(1, 250)
        id_pair = (user_id, movie_id)
        if id_pair not in id_pair_set:
            id_pair_set.add(id_pair)
            break

    rating = random.randint(3, 5)
    data_line = f"{user_id} {movie_id} {rating}\n"
    sql_line = f"INSERT INTO movie_ratings (user_id, movie_id, rating) VALUES ({user_id}, {movie_id}, {rating});\n"
    data_lines.append(data_line)
    sql_lines.append(sql_line)

with open(r'C:\Users\lenovo\Desktop\训练数据.txt', 'w') as f:
    for data_line in data_lines:
        f.write(data_line)
    for sql_line in sql_lines:
        f.write(sql_line)

