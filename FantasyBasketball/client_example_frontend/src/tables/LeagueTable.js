import {Component} from "react";
import {Table} from "semantic-ui-react";

class LeagueTable extends Component {

    render() {
        const {league} = this.props

        return (
            <Table.Row>
                <Table.Cell>{league.league_name}</Table.Cell>
                <Table.Cell>{league.league_size}</Table.Cell>
                <Table.Cell>{league.draft_finished ? 'YES' : 'NO'}</Table.Cell>
                <Table.Cell>{league.league_start_date}</Table.Cell>
                <Table.Cell>{league.num_weeks}</Table.Cell>
                <Table.Cell selectable positive><a href='#' onClick={() => this.props.redirect(league.league_id)}>More
                    Info</a></Table.Cell>
            </Table.Row>
        )
    }
}

export default LeagueTable