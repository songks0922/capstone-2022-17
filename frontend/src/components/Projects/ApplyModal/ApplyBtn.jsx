import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import media from 'utils/media';
import { Modal, Icon, Radio, Label } from 'semantic-ui-react';
import * as Btn from 'components/common/Btn';
import * as Container from 'components/common/Containers';
import { ADD_MEMBER_REQUEST } from 'reducers/member';

const SupplyBtn = styled(Btn.PrimaryBtn)`
  font-size: 0.9rem !important;
  border-radius: 3rem !important;
  height: 2.2rem !important;
  margin-top: 0.5rem !important;
  cursor: pointer;
`;

const ApplyModal = styled(Modal)`
  max-width: 35rem;
  ${media.tablet`
      max-width: 60%;
      min-width: 60%;
    `};
`;

const ModalHeader = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 1.3rem 1rem;
  border-bottom: 1px solid #d6d6d6;
`;

const ModalTitle = styled.div`
  font-family: 'Pr-SemiBold';
  font-size: 1.1rem;
`;

const ApplyBtn = ({ project }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { addMemberLoading } = useSelector((state) => state.member);
  const [open, setOpen] = useState(false);
  const [positions, setPositions] = useState([]);
  const [selected, setSelected] = useState('');

  const handleOpen = () => {
    setOpen(true);
    setPositions(project.projectPositions);
  };

  const handleSubmit = () => {
    dispatch({
      type: ADD_MEMBER_REQUEST,
      data: { positionName: selected, projectId: project.id },
    });
  };

  useEffect(() => {
    if (addMemberLoading) {
      navigate('/');
    }
  }, [addMemberLoading]);

  return (
    <ApplyModal
      open={open}
      onOpen={handleOpen}
      onClose={() => setOpen(false)}
      trigger={<SupplyBtn fluid>지원하기</SupplyBtn>}
    >
      <ModalHeader>
        <div />
        <ModalTitle>지원하기</ModalTitle>
        <Icon
          onClick={() => setOpen(false)}
          name="remove"
          style={{ cursor: 'pointer', opacity: '0.5' }}
        />
      </ModalHeader>
      <Modal.Content style={{ padding: '2rem' }}>
        <Container.ColumnMiddleContainer style={{ width: '100%' }}>
          <Container.ColumnStartContainer style={{ marginBottom: '1rem' }}>
            {positions.map((pos) => {
              return (
                <Container.AlignMiddleContainer style={{ marginBottom: '1rem' }}>
                  <Radio
                    key={pos.positionName}
                    label={pos.positionName}
                    value={pos.positionName}
                    checked={selected === pos.positionName}
                    onChange={(e, { value }) => setSelected(value)}
                  />
                  <Label style={{ padding: '0.4rem 0.6rem', marginLeft: '1rem' }}>
                    {pos.currentCnt} / {pos.total}
                  </Label>
                </Container.AlignMiddleContainer>
              );
            })}
          </Container.ColumnStartContainer>
          <Btn.PrimaryBtn onClick={handleSubmit}>이 포지션으로 지원하기</Btn.PrimaryBtn>
        </Container.ColumnMiddleContainer>
      </Modal.Content>
    </ApplyModal>
  );
};

export default ApplyBtn;
