from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/post_example', methods=['POST', 'GET'])
def post_example():
    result = {'title': 'Billionaires in the latest year accumulated more wealth through inheritance than entrepreneurship for the first time in almost a decade, according to UBS. The report reveals a growing trend of the "great wealth transfer" as aging billionaire entrepreneurs pass on an estimated $5.2 trillion to their heirs. Billionaire heirs are increasingly focused on global economic challenges, investing in sectors like clean energy and artificial intelligence. Europe led the growth in billionaire wealth, driven by a post-pandemic shopping surge benefiting luxury goods companies. UBS sees both opportunities and risks in this wealth transfer, emphasizing the importance of understanding potential beneficiaries. Younger generations show a preference for impact investing over traditional philanthropy.'}

    # Return a JSON response
    return jsonify(result)

if __name__ == '__main__':
    app.run(debug=True, port=5000)
