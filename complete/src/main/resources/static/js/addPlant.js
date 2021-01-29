function addPlant() {
    const formPlantName = document.getElementById("plantName").value;
    const formWateringInterval = document.getElementById("wateringInterval").value;
    var formWatered = $("input[name=watered]:checked").val();
    var formLastWatered;

    if (formWatered === "yes") {
        formLastWatered = document.getElementById("lastWatered").value;
    }
    else if (formWatered === "no") {
        const formNextWatering = document.getElementById("nextWatering").value;
        var dt = new Date(formNextWatering);
        dt.setDate(dt.getDate() - formWateringInterval);
        formLastWatered = dt;
    }

    $.ajax({
        type: 'POST',
        url: 'addPlant',
        data: JSON.stringify(
            {plantName: formPlantName,
            wateringInterval: formWateringInterval,
            lastWatered: formLastWatered
        }),
        success: function(response) {
            if (formWatered === "yes") {
                //initiate addition to waterings
            }
            displayMessage(response.message);
        },
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

function displayMessage(message) {
    const myMessage = <Message message={message} />;
    ReactDOM.render(
        myMessage,
        document.getElementById('messagePlaceholder')
    );
}