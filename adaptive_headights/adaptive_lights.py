import pygame
import math
pygame.init()

game_display = pygame.display.set_mode((800 , 600))
pygame.display.set_caption('headlight simulation')

white = (255,255,255)
clock = pygame.time.Clock()
crashed = False
img = pygame.image.load('car.png')
# other_img = pygame.image.load('red_car.png')

# print(input().strip().split(' '))
# print(raw_input())
# x1 , y1 =  map(int,raw_input().split())
# x2 , y2 =  map(int,raw_input().split())


def car_headlight(x_init , y_init , theta , d):
    # game_display.blit(carImg, (x,y))	
	trans_image = pygame.transform.rotate(img, -theta)
	if x_init > 700:
		theta = 10	
	game_display.blit(trans_image,(x_init , y_init))
	x_init , y_init = int(x_init) , int(y_init)
	pygame.draw.ellipse(game_display, (255,255,0), [x_init,y_init, 10-d, 10], 0)
	pygame.draw.ellipse(game_display, (255,255,0), [x_init+20,y_init, 10-d, 10], 0)
	pygame.draw.circle(game_display,(255,255,0),(700,500),300 , 1)
	pygame.draw.circle(game_display,(255,255,0),(700,500),600 , 1)

def car(x_init , y_init , theta):
    # game_display.blit(carImg, (x,y))	
	trans_image = pygame.transform.rotate(img, -theta)
	if x_init > 700:
		theta = 10	
	game_display.blit(trans_image,(x_init , y_init))
	x_init , y_init = int(x_init) , int(y_init)
	pygame.draw.circle(game_display,(255,255,0),(x_init,y_init),5)
	pygame.draw.circle(game_display,(255,255,0),(x_init+20,y_init),5)
	pygame.draw.circle(game_display,(255,255,0),(700,500),300 , 1)
	pygame.draw.circle(game_display,(255,255,0),(700,500),600 , 1)
    # pygame.draw.circle(game_display , (255 , 255 , 0) , (300,300) , 5)

x_c , y_c , radius = 700 , 500 , 450
d = 0
theta = 10
while True:

	d += 0.001
	x = x_c + radius*math.cos(theta/10)
	y = y_c + radius*math.sin(theta/10)
	theta += 0.1
	# print(sw , sh)
	
	game_display.fill((0 , 0 , 0))
	car_headlight(x,y,theta,d)
	# rotated_damage = pygame.transform.rotate(circle_hitbox["damage"], -circle_hitbox["angle"])
	pygame.display.update()
	clock.tick(60)	

pygame.quit()
quit()

'''

I have the radius of the path , and I have the radius of curvature too . 
the lihts would be oval when int tuns

'''

					