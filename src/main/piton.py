from flask import Flask, request, jsonify, redirect, url_for

app = Flask(__name__)


@app.route('/receive_nothing_send_flask', methods=['POST'])
def receive_nothing_send_flask():
    result = {'title': 'mock-title'}

    return jsonify(result)

@app.route('/receive_java_send_flask', methods=['GET', 'POST'])
def receive_java_redirect_flask():
    data = request.json
    print("recieved data: ", data)

    result = {'title': 'mock-summary'}
    return redirect(url_for('receive_flask_send_java', processed_data=result))

#post e pus for saftey deocamdata
@app.route('/receive_flask_send_java', methods=['GET', 'POST'])
def receive_flask_send_java():
    processed_data = request.args.get('processed_data')
    print("received flask data: ", processed_data)

    return jsonify(processed_data)

if __name__ == '__main__':
    app.run(debug=True, port=5000)

