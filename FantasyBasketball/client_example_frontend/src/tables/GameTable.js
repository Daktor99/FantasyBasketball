import {Component} from "react";
import {Table} from "semantic-ui-react";

class GameTable extends Component {

    async componentWillMount() {

    }

    render() {
        const {game} = this.props

        return (
            <Table.Row>
                <Table.Cell>{game.team_name}</Table.Cell>
                <Table.Cell>{game.game_start_date}</Table.Cell>
                <Table.Cell>{game.game_end_date}</Table.Cell>
                <Table.Cell>{game.home_points}</Table.Cell>
                <Table.Cell>{game.away_points}</Table.Cell>
                <Table.Cell>{game.winner_id ? game.winner_id : "No winner yet"}</Table.Cell>
            </Table.Row>
        )
    }
}

export default GameTable