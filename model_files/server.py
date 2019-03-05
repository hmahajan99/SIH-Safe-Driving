# Import libraries
import numpy as np
import keras
import pandas as pd
import os
from flask import Flask, request, jsonify
import pickle
from keras.models import load_model

model = load_model('my_model.h5')
model._make_predict_function()
# prediction = model.predict(np.array([[0.21531516644139326,0.15005541484228635,0.7914562008951398,-2.,39.,-62.,76.,55.,65.]]))
# print(prediction)

app = Flask(__name__)
# Load the model

@app.route('/api',methods=['POST'])

def predict():
    # Get the data from the POST request.
    data = request.get_json(force=True)
    # Make prediction using model loaded from disk as per the data.
    # prediction = model.predict([[np.array(data['exp'])]])
    # prediction = model.predict(np.array([data['exp']]))
    prediction = model.predict(np.array([[0.21531516644139326,0.15005541484228635,0.7914562008951398,-2.,39.,-62.,76.,55.,65.]]))
    # Take the first value of prediction
    # output = prediction[0]
    print('server')
    print(prediction)
    # print(data)
    return jsonify({'vfx' : str(prediction[0][0]) , 'vfy' :str(prediction[0][1])})
if __name__ == '__main__':
    app.run(port=5000, debug=True)