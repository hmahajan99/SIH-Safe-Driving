import geocoder
import pandas as pd
g = geocoder.ip('me')
print(g.latlng)
import random
import numpy as np
x , y , acc_val = [] , [] , []
def coordinates(var):
    for _ in range(100):
        var += random.choice([-0.001 , 0.001 , 0.002 , -0.002])
    return var

for _ in range(100):
    x.append(coordinates(g.latlng[0]))
    y.append(coordinates(g.latlng[1]))
    acc_val.append(np.random.randint(5 , 100))

data = pd.DataFrame({'Latitude' : x , 'Longitude' : y , 'Accident_value' : acc_val})
data.to_csv('accident_data.csv' , sep = ',' , header = True , index = False)