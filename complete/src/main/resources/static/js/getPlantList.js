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
        this.state = {watered: false,
                      lastWatering: null}
        this.waterPlant = this.waterPlant.bind(this)
        this.undoWater = this.undoWater.bind(this)
        this.removePlant = this.removePlant.bind(this)
    }
    waterPlant() {
        $.ajax({
                type: 'POST',
                url: 'waterPlant',
                data: JSON.stringify({plantID: this.props.plantID}),
                success: (response) => {this.setState({lastWatering: response.watering});
                                        if (response.message != null) {alert(response.message)};},
                contentType: 'application/json',
                dataType: 'json'
            });
        this.setState({watered: true});
    }
    undoWater() {
        const req = {watering: this.state.lastWatering};
        $.ajax({
                type: 'POST',
                url: 'undoWater',
                data: JSON.stringify(req),
                success: function(response) {if (response.message != null) {alert(response.message)}},
                contentType: 'application/json',
                dataType: 'json'
                });
        this.setState({watered: false});
    }
    removePlant() {
        $.ajax({
                type: 'POST',
                url: 'removePlant',
                data: JSON.stringify({plantID: this.props.plantID}),
                success: function(response) {alert(response.message);},
                contentType: 'application/json',
                dataType: 'json'
            })
    }
    render () {
        let waterButton;
        if (this.state.watered) {
            waterButton = <button onClick={this.undoWater}>Undo Watering</button>;
        } else {
            waterButton = <button onClick={this.waterPlant}>Water</button>;
        }
        return (
            <div>
                <p>Plant name: {this.props.plantName}</p>
                <p>Watering interval: {this.props.wateringInterval}</p>
                <p>Last watered: {this.props.lastWatered}</p>
                <p>{waterButton}</p>
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
                    <PlantPanel plantID={plant.plantID}
                                plantName={plant.plantName}
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