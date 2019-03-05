#initial input
import matplotlib.pyplot as plt
k = int(input())
vix , viy = map(float , input().split())
total = []

#took data
for _ in range(k):
	x1 , y1 , vx1 , vy1 = map(float , input().split())
	total.append((x1 , y1 , vx1 , vy1))

max_coord = None
max_parameter = None

for i in range(k):
	parameter = total[i][0]*total[i][2] + total[i][1]*total[i][3]
	x , y = total[i][0] + 0.00001, total[i][1] + 0.000001
	if parameter < 0 :
		if max_coord is None:
			max_coord = total[i]
			max_parameter = abs(parameter)/(x*x +y*y)
		elif abs(parameter)/(x*x +y*y) > max_parameter:
			max_coord = total[i]
			max_parameter = abs(parameter)/(x*x +y*y)


print("Frame 1 : ",max_coord)
#conditional based on coord
vehicle_x,vehicle_y = max_coord[2],max_coord[3]
normal_x,normal_y = None,None 
if vehicle_x > 0 and vehicle_y > 0 :
	normal_x = -vehicle_y
	normal_y = vehicle_x
elif vehicle_x < 0 and vehicle_y > 0 :
	normal_x = vehicle_y
	normal_y = -vehicle_x
elif vehicle_x < 0 and vehicle_y < 0 :
	normal_x = vehicle_y
	normal_y = -vehicle_x
elif vehicle_x > 0 and vehicle_y < 0 :
	normal_x = -vehicle_y
	normal_y = vehicle_x
elif vehicle_y == 0:
	normal_x = 0
	normal_y = abs(vehicle_x)
else:
	normal_x = abs(vehicle_y)
	normal_y = 0

vfx = vix + normal_x
vfy = viy + normal_y

print("Best Decision : ",str(vfx) + ' i + ' , str(vfy) + ' j')

'''
==============================
example case 1:

3
0 1
-1 -1 1.4142 1.4142
0 -2 0 1
1 0 -1 0
(-1.0, -1.0, 1.4142, 1.4142)
-1.4142 i + 2.4142 j

==============================
example case 2:

3
0 2
-1 1 1.4142 1.4142
3 0 -3 0
0 -5 0 4

'''

plt.scatter(0 , 0 , color = 'green')
for i in range(k):
	plt.scatter(total[i][0] , total[i][1] , color = 'blue')
plt.scatter(max_coord[0] , max_coord[1] , color = 'red')
plt.show()


## For further frames after t units time
t = 0.5
i = 2
while True :

	xc,yc = vfx*t , vfy*t
	totalNew = []
	for i in total:
		totalNew.append(( i[0] + i[2]*t - xc , i[1]+ i[3]*t - yc , i[2] , i[3] ))

	total = totalNew

	max_coord = None
	max_parameter = None

	for i in range(k):
		parameter = total[i][0]*total[i][2] + total[i][1]*total[i][3]
		x , y = total[i][0] + 0.00001, total[i][1] + 0.000001
		if parameter < 0 :
			if max_coord is None:
				max_coord = total[i]
				max_parameter = abs(parameter)/(x*x +y*y)
			elif abs(parameter)/(x*x +y*y) > max_parameter:
				max_coord = total[i]
				max_parameter = abs(parameter)/(x*x +y*y)


	print("Frame " + str(i) + " : " , max_coord)
	i+=1

	#conditional based on coord
	vehicle_x,vehicle_y = max_coord[2],max_coord[3]
	normal_x,normal_y = None,None 
	if vehicle_x > 0 and vehicle_y > 0 :
		normal_x = -vehicle_y
		normal_y = vehicle_x
	elif vehicle_x < 0 and vehicle_y > 0 :
		normal_x = vehicle_y
		normal_y = -vehicle_x
	elif vehicle_x < 0 and vehicle_y < 0 :
		normal_x = vehicle_y
		normal_y = -vehicle_x
	elif vehicle_x > 0 and vehicle_y < 0 :
		normal_x = -vehicle_y
		normal_y = vehicle_x
	elif vehicle_y == 0:
		normal_x = 0
		normal_y = abs(vehicle_x)
	else:
		normal_x = abs(vehicle_y)
		normal_y = 0

	vfx = vix + normal_x
	vfy = viy + normal_y

	print("Best Decision : ",str(vfx) + ' i + ' , str(vfy) + ' j')



	plt.scatter(0 , 0 , color = 'green')
	for i in range(k):
		plt.scatter(total[i][0] , total[i][1] , color = 'blue')
	plt.scatter(max_coord[0] , max_coord[1] , color = 'red')
	plt.show()

	print("Continue simulation ? (y/n) ")
	choice = input()
	if choice=='n':
		break