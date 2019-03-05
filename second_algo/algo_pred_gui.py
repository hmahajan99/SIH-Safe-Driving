import matplotlib.pyplot as plt
from tkinter import *
# from Tkinter import *
fields = ('num_vehicles', 'vehicle1', 'vehicle2', 'vehicle3' , 'curr_vehicle' , 'number_of_iter', 'vfx' , 'vfy')

def find_vel(entries):
   # period rate:
   k = int(entries['num_vehicles'].get())
   print("r", k)
   # principal loan:
   v1 = [float(val) for val in entries['vehicle1'].get().split(',')]
   v2 =  [float(val) for val in entries['vehicle2'].get().split(',')]
   v3 =  [float(val) for val in entries['vehicle3'].get().split(',')]
   v_curr =  [float(val) for val in entries['curr_vehicle'].get().split(',')]
   vix , viy = v_curr[0] , v_curr[1]


   total = [(v1[0] , v1[1] ,v1[2] , v1[3]) , (v2[0] , v2[1] ,v2[2] , v2[3]) , (v3[0] , v3[1] ,v3[2] , v3[3])]

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


   lenval = 0.5
   plt.scatter(0 , 0 , color = 'green')
   plt.xlim(-10 , 10)
   plt.ylim(-10 , 10)
   for i in range(k):
       plt.scatter(total[i][0] , total[i][1] , color = 'blue')
       plt.text(total[i][0] * (1 + 0.01),  total[i][1]* (1 + 0.01) , str(total[i][2]) + 'i +' +
         str(total[i][3]) + 'j', fontsize=8)

       u1, u2 = None , None
       if total[i][2] > 0:
         u1 = lenval
       else:
         u1 = -lenval
       if total[i][3] > 0:
         u2 = lenval
       else:
         u2 = -lenval
       if total[i][2] == 0:
         u1 = 0
       if total[i][3] == 0:
         u2 = 0

       
       plt.annotate( "", xy=(total[i][0] + u1, total[i][1] + u2), xytext=(total[i][0],total[i][1]),
                 arrowprops=dict( arrowstyle="->" ) )
   u1, u2 = None , None
   if vix > 0:
      u1 = lenval
   else:
      u1 = -lenval
   if viy > 0:
      u2 = lenval
   else:
      u2 = -lenval
   if vix == 0:
      u1 = 0
   if viy == 0:
      u2 = 0
   plt.scatter(max_coord[0] , max_coord[1] , color = 'red')
   plt.text(0.01, 0.01 , str(vix) + 'i +' +
         str(viy) + 'j', fontsize=8)
   plt.annotate( "", xy=(u1, u2), xytext=(0,0),
                 arrowprops=dict( arrowstyle="->" ) )
   plt.show()


   print(vfx , vfy)

   t = 0.5
   ic = 2
   while ic<= int(entries['number_of_iter'].get()):

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


      print("Frame " + str(ic) + " : " , max_coord)
      ic+=1

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

      plt.xlim(-10 , 10)
      plt.ylim(-10 , 10)
      plt.scatter(0 , 0 , color = 'green')
      for i in range(k):
         plt.scatter(total[i][0] , total[i][1] , color = 'blue')

         u1, u2 = None , None
         if total[i][2] > 0:
            u1 = lenval
         else:
            u1 = -lenval
         if total[i][3] > 0:
            u2 = lenval
         else:
            u2 = -lenval
         if total[i][2] == 0:
            u1 = 0
         if total[i][3] == 0:
            u2 = 0

         plt.text(total[i][0] * (1 + 0.01),  total[i][1]* (1 + 0.01) , str(total[i][2]) + 'i +' +
            str(total[i][3]) + 'j', fontsize=8)
         plt.annotate( "", xy=(total[i][0] + u1, total[i][1] + u2), xytext=(total[i][0],total[i][1]),
                 arrowprops=dict( arrowstyle="->" ) )

         u1, u2 = None , None
         if vfx > 0:
            u1 = lenval
         else:
            u1 = -lenval
         if vfy > 0:
            u2 = lenval
         else:
            u2 = -lenval
         if vfx == 0:
            u1 = 0
         if vfy == 0:
            u2 = 0
      plt.text(0.01, 0.01 , str(vfx) + 'i +' +
         str(vfy) + 'j', fontsize=8)
      plt.scatter(max_coord[0] , max_coord[1] , color = 'red')
      plt.annotate( "", xy=(u1, u2), xytext=(0,0),
                 arrowprops=dict( arrowstyle="->" ) )
      plt.show()

      
      

def makeform(root, fields):
   entries = {}
   for field in fields:
      row = Frame(root)
      lab = Label(row, width=22, text=field+": ", anchor='w')
      ent = Entry(row)
      ent.insert(0,"0")
      row.pack(side=TOP, fill=X, padx=5, pady=5)
      lab.pack(side=LEFT)
      ent.pack(side=RIGHT, expand=YES, fill=X)
      entries[field] = ent
   return entries

if __name__ == '__main__':
   root = Tk()
   ents = makeform(root, fields)
   root.bind('<Return>', (lambda event, e=ents: fetch(e)))   
   # b1 = Button(root, text='Final Balance',
   #        command=(lambda e=ents: final_balance(e)))
   # b1.pack(side=LEFT, padx=5, pady=5)
   variable = StringVar(root)
   variable.set("one") # default value
   w = OptionMenu(root, variable, "one", "two", "three")
   w.pack()
   b2 = Button(root, text='velocities!',
          command=(lambda e=ents: find_vel(e)))
   b2.pack(side=LEFT, padx=5, pady=5)
   b3 = Button(root, text='Quit', command=root.quit)
   b3.pack(side=LEFT, padx=5, pady=5)
   
   def ok():
      print('values is ' , var.get())
      root.quit()

   b1 = Button(root , text = 'OK' , command = ok)
   b1.pack()

   root.mainloop()

   '''
   3
   -1,-1,1.41,1.41
   1,0,-1,0
   0,-2,0,1
   0,1
   '''