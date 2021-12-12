import {Component} from "react";
import {Button, Table} from "semantic-ui-react";

class GameTable extends Component {

    async componentWillMount() {

    }

    render() {
        const {game} = this.props

        return (
            <Table.Row>
                <Table.Cell>{game.game_start_date}</Table.Cell>
                <Table.Cell>{game.game_end_date}</Table.Cell>
                <Table.Cell>{game.home_points}</Table.Cell>
                <Table.Cell>{game.away_points}</Table.Cell>
                <Table.Cell>{game.winner_id}</Table.Cell>
            </Table.Row>
        )
    }
}

export default GameTable