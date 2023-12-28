from urllib.parse import unquote
from flask import Flask, request, jsonify, redirect, url_for
import requests

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
    #do stuff
    if request.method == 'POST':
        print("received data: ", data)
        result = {'title': 'mock_summary1'}
        return jsonify(result)
    else:
        print("no data found")
        result = {'title': 'NOT_FOUND'}
        return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True, port=5000)

