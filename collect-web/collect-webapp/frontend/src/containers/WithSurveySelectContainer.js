import React, { Component } from 'react';
import { Container } from 'reactstrap';
import { connect } from 'react-redux';
import SurveySelect from 'components/SurveySelect/';

class WithSurveySelectContainer extends Component {

    render() {
        const { survey }  = this.props
        return (
            <Container fluid>
                <SurveySelect />
                {survey && 
                    <div animate fade-in>
                        {this.props.children}
                    </div>
                }
            </Container>
        )
    }
}

const mapStateToProps = state => {
	return {
		survey: state.preferredSurvey ? state.preferredSurvey.survey : null
	}
}

export default connect(mapStateToProps)(WithSurveySelectContainer)