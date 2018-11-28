import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { QuikzSharedModule } from 'app/shared';
import { QuikzAdminModule } from 'app/admin/admin.module';
import {
    QuestionComponent,
    QuestionDetailComponent,
    QuestionUpdateComponent,
    QuestionDeletePopupComponent,
    QuestionDeleteDialogComponent,
    questionRoute,
    questionPopupRoute
} from './';

const ENTITY_STATES = [...questionRoute, ...questionPopupRoute];

@NgModule({
    imports: [QuikzSharedModule, QuikzAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuestionComponent,
        QuestionDetailComponent,
        QuestionUpdateComponent,
        QuestionDeleteDialogComponent,
        QuestionDeletePopupComponent
    ],
    entryComponents: [QuestionComponent, QuestionUpdateComponent, QuestionDeleteDialogComponent, QuestionDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class QuikzQuestionModule {}
