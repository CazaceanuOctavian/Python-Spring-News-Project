from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/post_example', methods=['POST'])
def post_example():
    result = {'title': 'Hello cruel world!'}

    # Return a JSON response
    return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True, port=5000)