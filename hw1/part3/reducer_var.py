import sys

# Переменные для подсчета среднего
cur_cnt = 0
cur_mean = 0
cur_var = 0

# Стандартный инпут
for i, line in enumerate(sys.stdin):
	# if i >= 2:
	# 	break

	cnt, mean, var = line.split('\t')
	cnt = int(cnt)
	mean = float(mean)
	var = float(var)

	# Пересчитываем текущую дисперсию и количество
	var_term1 = (cur_cnt * cur_var + cnt * var) / (cur_cnt + cnt)
	var_term2 = cur_cnt * cnt * ((cur_mean - mean) / (cur_cnt + cnt))**2
	cur_var = var_term1 + var_term2
	cur_mean = (cur_cnt * cur_mean + cnt * mean) / (cur_cnt + cnt)
	cur_cnt = cur_cnt + cnt

	# print(cur_cnt, var_term1, var_term2, cur_mean, cur_var)


# Финальный результат(Может являться инпутом в редусер следующего уровня)
print(f'{cur_cnt}\t{cur_mean}\t{cur_var}')
