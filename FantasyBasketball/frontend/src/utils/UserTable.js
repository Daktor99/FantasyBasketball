import {Component} from "react";
import {Table} from "semantic-ui-react";

class UserTable extends Component {

    render() {
        return (
            <Table.Row>
                <Table.Cell>{this.props.user.first_name}</Table.Cell>
                <Table.Cell>{this.props.user.last_name}</Table.Cell>
                <Table.Cell>{this.props.user.email}</Table.Cell>
            </Table.Row>
        )
    }
}

export default UserTable