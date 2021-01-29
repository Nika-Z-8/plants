function submitName() {
    const formName = $("#name").val();
    $.ajax({
        type: 'POST',
        url: 'greeting',
        data: JSON.stringify({name: formName}),
        success: function(response) {displayMessage(response.content);},
        contentType: 'application/json',
        dataType: 'json'
    });
}

class Message extends React.Component {
    render () {
        return (
            <div>
                {this.props.message}
            </div>
        );
    }
}

function displayMessage(m) {
    const myMessage = <Message message={m} />;
    ReactDOM.render(
        myMessage,
        document.getElementById('messagePlaceholder')
    );
}