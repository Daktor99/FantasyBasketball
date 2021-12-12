import {Component} from "react";
import {Button, Table} from "semantic-ui-react";

class TeamTable extends Component {

    render() {
        const {team} = this.props

        return (
            <Table.Row>
                <Table.Cell>{team.team_name}</Table.Cell>
                <Table.Cell>{team.team_wins}</Table.Cell>
                <Table.Cell>{team.team_losses}</Table.Cell>
                <Table.Cell>{team.points_scored}</Table.Cell>
                <Table.Cell>{team.points_against}</Table.Cell>
                <Table.Cell><Button onClick={() => this.props.redirect(team.team_id)}>More Info</Button></Table.Cell>
            </Table.Row>
        )
    }
}

export default TeamTable