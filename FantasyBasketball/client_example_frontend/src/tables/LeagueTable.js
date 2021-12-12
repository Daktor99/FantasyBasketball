import {Component} from "react";
import {Button, Table} from "semantic-ui-react";

class LeagueTable extends Component {

    render() {
        const {league} = this.props

        return (
            <Table.Row>
                <Table.Cell>{league.league_name}</Table.Cell>
                <Table.Cell>{league.league_size}</Table.Cell>
                <Table.Cell>{league.draft_finished ? 'NO' : 'YES'}</Table.Cell>
                <Table.Cell>{league.league_start_date}</Table.Cell>
                <Table.Cell>{league.num_weeks}</Table.Cell>
                <Table.Cell><Button onClick={() => this.props.redirect(league.league_id)}>More
                    Info</Button></Table.Cell>
            </Table.Row>
        )
    }
}

export default LeagueTable