import React, {useState} from 'react';
import '../styles/AgreeModal.css';



// const history = useHistory();
const AgreeModal = ({closeModal}) => {
    const [agreeChecked, setAgreeChecked] = useState(false);
    const [scrolledToEnd, setScrolledToEnd] = useState(false);

    const handleAgreeChecked = () => {
        setAgreeChecked(prevState => !prevState);
    }

    const handleScroll = (e) => {
        const {scrollTop, scrollHeight, clientHeight} = e.target;
        const scrolled = (scrollTop / (scrollHeight - clientHeight)) * 100;
        if (scrolled > 90) { // 스크롤이 90% 이상 내려갔을 경우
            setScrolledToEnd(true);
        }
    }



    return (
        <div className="modal">
            <div className="agreeModal">
                <button className="close" onClick={closeModal}>X</button>
                <div className="modalContents" onScroll={handleScroll}>
                    <p>투자 약관 설명</p>
                    증권투자는 본인의 판단과 책임으로.
                    증권투자는 반드시 자기 자신의 판단과 책임하에 하여야 하며, 자신의 여유자금으로 분산투자하는 것이 좋습니다.
                    증권회사 직원 및 타인에게 매매거래를 위임하더라도 투자손익은 고객 자신에게 귀속되며, 투자원금의 보장 또는 손실보전 약속은 법률적으로 효력이 없습니다.
                    높은 수익에는 높은 위험이.
                    높은 수익에는 반드시 높은 위험이 따른다는 것을 기억하고 투자시 어떤 위험이 있는지 반드시 확인하시기 바랍니다.
                    주식워런트 증권(ELW), 선물·옵션 거래는 단기간에 투자금 전부를 손실 볼 수 있으며 특히 선물·옵션 거래는 투자금을 초과하여 손실 볼 수 있으므로 거래설명서를 교부받고 거래제도와 특성, 거래에 따른 위험 등을 반드시 숙지하여야 합니다.
                    계좌관련 정보, 증권카드 등은 본인이 직접 관리해야.
                    주민등록번호, 계좌번호, 비밀번호, HTS 아이디 등을 남에게 알리거나, 거래인감, 증권카드, 보안카드 등을 남에게 맡겨서는 절대 안됩니다.
                    주소, 전화번호 등 고객정보가 변경된 경우에는 회사에 반드시 통보하여 권리행사와 관련된 불이익을 받지 않도록 하여야 합니다.
                    매매거래에 이상이 있는지 수시로 확인해야.
                    전화주문 및 투자상담을 하는 경우에는 녹음되는 지점 전화를 이용하고, 매매체결 또는 월간거래 내역을 살펴보아 매매거래에 이상이 있는 경우 즉시 거래를 중단하고 감사실(02-768-3957, 3974, 3981, 3991) 등에 이의를 제기하여야 합니다.
                    사이버거래시에는 장애발생시 대처요령을 숙지하여 만일에 경우에 대비하여야 합니다.
                </div>
                {scrolledToEnd &&
                    <>
                        <input type="checkbox" checked={agreeChecked} onChange={handleAgreeChecked} />
                        <button className="agree-button" onClick={closeModal} disabled={!agreeChecked}>
                            동의하기
                        </button>
                    </>
                }
            </div>
        </div>
    );
}

export default AgreeModal;
