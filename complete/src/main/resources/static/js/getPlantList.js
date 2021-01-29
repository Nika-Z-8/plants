function getPlantList() {
    $.ajax({
        type: 'POST',
        url: 'getPlantList',
        data: {},
        success: function(response) {displayPlantList(response.plantList, response.message);},
        contentType: 'application/json',
        dataType: 'json'
    });
}

class PlantPanel extends React.Component {
    constructor(props) {
        super(props)
        this.waterPlant = this.waterPlant.bind(this)
        this.removePlant = this.removePlant.bind(this)
    }
    waterPlant() {
        alert("watering")
    }
    removePlant() {
        $.ajax({
                type: 'POST',
                url: 'removePlant',
                data: JSON.stringify({plantName: this.props.plantName}),
                success: function(response) {alert(response.message);},
                contentType: 'application/json',
                dataType: 'json'
            })
    }
    render () {
        return (
            <div>
                <p>Plant name: {this.props.plantName}</p>
                <p>Watering interval: {this.props.wateringInterval}</p>
                <p>Last watered: {this.props.lastWatered}</p>
                <p><button onClick={this.waterPlant}>Water</button></p>
                <p><button onClick={this.removePlant}>Remove</button></p>
                <br />
            </div>
        );
    }
}

class PlantList extends React.Component {
    render () {
        const plantList = this.props.plantList;
        return (
            <div>
                {plantList.map((plant) =>
                    <PlantPanel plantName={plant.plantName}
                                wateringInterval={plant.wateringInterval}
                                lastWatered={plant.lastWatered}
                    />
                )}
            </div>
        );
    }
}

function displayPlantList(plantList, message) {
    if (message != null) alert(message);
    const myPlantList = <PlantList plantList={plantList}/>;
    ReactDOM.render(
        myPlantList,
        document.getElementById('plantListPlaceholder')
    );
}

getPlantList();