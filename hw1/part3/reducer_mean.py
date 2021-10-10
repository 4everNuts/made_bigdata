import sys

# Переменные для подсчета среднего
cur_cnt = 0
cur_mean = 0

# Стандартный инпут
for i, line in enumerate(sys.stdin):
	cnt, mean = line.split('\t', 1)
	cnt = int(cnt)
	mean = float(mean)

	# Пересчитываем текущее среднее и количество
	cur_mean = (cur_cnt * cur_mean + cnt * mean) / (cur_cnt + cnt)
	cur_cnt = cur_cnt + cnt

# Финальный результат(Может являться инпутом в редусер следующего уровня)
print(f'{cur_cnt}\t{cur_mean}')
