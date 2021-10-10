import pandas as pd
import numpy as np

df = pd.read_csv('AB_NYC_2019.csv')
print(np.mean(df.price))
print(np.var(df.price))
