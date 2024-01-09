import json
import random
import string
import numpy as np
import requests
import trafilatura
from urllib.parse import unquote
from bs4 import BeautifulSoup
from flask import Flask, request, jsonify, redirect, url_for
from requests.models import MissingSchema

app = Flask(__name__)

@app.route('/receive_nothing_send_flask', methods=['POST'])
def receive_nothing_send_flask():
    result = {'title': 'mock-title'}

    return jsonify(result)

#works
#=====================================================
#dated and not working
@app.route('/receive_java_send_flask', methods=['POST'])
def receive_java_redirect_flask():
    data = request.json
    print("recieved data: ", data)
    #do AI suff
    if request.method == 'POST':
        result = {'title': 'mock-summary1'}
        return redirect(url_for('receive_flask_send_java', processed_data=result))
    else:
        return "err"

@app.route('/receive_flask_send_java', methods=['GET', 'POST'])
def receive_flask_send_java():
    processed_data = request.args.get('processed_data')
    if processed_data == None:
        print("no data found")
        result = {'title': 'NOT_FOUND'}
        return jsonify(result)
    else:
        print("received flask data: ", processed_data)
        json_data_decoded = unquote(processed_data)
        json_data_normalized = json_data_decoded.replace("'", "\"")
        return json_data_normalized
    
#=========================================================================
#testing
    
@app.route('/receive_java_send_java', methods = ['GET', 'POST'])
def recieve_java_send_java():
    data=request.json
    #doing stufff
    transormDataToString = json.dumps(data)
    parsedData = json.loads(transormDataToString)

    url = parsedData['url']

    downloaded_url = trafilatura.fetch_url(url)
    try:
        a = trafilatura.extract(downloaded_url, output_format = 'json', with_metadata=True, include_comments = False,
                            date_extraction_params={'extensive_search': True, 'original_date': True})
    except AttributeError:
        a = trafilatura.extract(downloaded_url, output_format = 'json', with_metadata=True,
                            date_extraction_params={'extensive_search': True, 'original_date': True})
    if a:
        json_output = json.loads(a)
        payload = json_output['raw_text']
    else:
        json_output = 'ERR'
        payload = 'ERR'

    #ended doing stuff
    if request.method == 'POST':
        return jsonify(payload)
    else:
        print("no data found")
        result = {'summarizedContent': 'NOT_FOUND'}
        return jsonify(result)
    

if __name__ == '__main__':
    app.run(debug=True, port=5000)

