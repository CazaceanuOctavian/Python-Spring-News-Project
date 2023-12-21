from flask import Flask, request, jsonify

app = Flask(__name__)


@app.route('/post_example', methods=['POST'])
def post_example():
    result = {'title': 'mock-title'}

    return jsonify(result)

@app.route('/receive_example', methods=['GET', 'POST'])
def receive_data():
    data = request.json
    print("recieved data: ", data)
    
    return jsonify(data)


if __name__ == '__main__':
    app.run(debug=True, port=5000)

