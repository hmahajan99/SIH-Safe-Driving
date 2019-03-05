import random
import numpy as np  
import pandas as pd  

Y1 , Y2 , Y3 , Vx1 , Vx2 , Vx3 ,Vy1 , Vy2 , Vy3 , Vix , Viy , Vfx , Vfy = [] , [] , [] , [] , [] , [] , [] , [] , [] , []  , [] , [] , []
def best_velocity(vx1 , vx2 , vx3 , vy1 , vy2 , vy3 , vix , viy):
	if random.random() < 0.9:
		vfx = vix + (-vy1 + vy2 + vy3)
		vfy = viy + vx1 + vx3
		return vfx , vfy
	else:
		return random.random() , random.random()

for _ in range(1000):
	# x1 , x2 , x3 = random.random() , random.random() , random.random()
	vix , viy = random.randint(0 , 100) , random.randint(0 , 100)
	y1 , y2 , y3 = random.random() , random.random() , random.random()
	vx1 , vx2 , vx3 = random.randint(-35 , 100) , random.randint(0 , 100) , random.randint(-100 , 50)
	vy1 , vy2 , vy3 = random.randint(0 , 100) , random.randint(0 , 100) , random.randint(0 , 100)
	vfx , vfy = best_velocity(vx1 , vx2 , vx3 , vy1 , vy2 , vy3 , vix , viy)
	Y1.append(y1)
	Y2.append(y2)
	Y3.append(y3)
	Vx1.append(vx1)
	Vx2.append(vx2)
	Vx3.append(vx3)
	Vy1.append(vy1)
	Vy2.append(vy2)
	Vy3.append(vy3)
	Vfx.append(vix)
	Vfy.append(viy)
data = pd.DataFrame({'pos1' : Y1 , 'pos2' : Y2 , 'pos3' : Y3 ,
	'vel_x1' : Vx1 , 'vel_x2' : Vx2 , 'vel_x3' : Vx3 , 
	'vel_y1' : Vy1 , 'vel_y2' : Vy2 , 'vel_y3' : Vy3 ,
	'final_x' : Vfx , 'final_y' : Vfy})

data.to_csv('coordibnate_data.csv' , index = False , header = None)
