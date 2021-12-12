import {Component} from "react";
import {Table} from "semantic-ui-react";

class LeagueTable extends Component {

    render() {
        return (
            <Table.Row>
                <Table.Cell>{this.props.league.league_name}</Table.Cell>
                <Table.Cell>{this.props.league.draft_finished}</Table.Cell>
                <Table.Cell>{this.props.league.league_start_date}</Table.Cell>
                <Table.Cell>{this.props.league.num_weeks}</Table.Cell>
            </Table.Row>
        )
    }
}

export default LeagueTable